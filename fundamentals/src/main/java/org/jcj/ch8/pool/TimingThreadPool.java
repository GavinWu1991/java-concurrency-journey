package org.jcj.ch8.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TimingThreadPool extends ThreadPoolExecutor {

    private static final Logger log = LogManager.getLogger(TimingThreadPool.class);
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public TimingThreadPool(int corePoolSize, boolean shouldStartAllCoreThreads) throws WarnUpException {
        super(corePoolSize, corePoolSize, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        if (shouldStartAllCoreThreads) {
            startAllCoreThreads(corePoolSize);
        }
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        log.trace("Thread {}: start {}", t, r);
        startTime.set(System.nanoTime());

    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            log.trace("Thread {}: end {}, time={}ns", t, r, taskTime);
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            log.info("Terminated: avg time={}ns", totalTime.get() / numTasks.get());
        } finally {
            super.terminated();
        }
    }

    private void startAllCoreThreads(int corePoolSize) throws WarnUpException {
        CountDownLatch warnUpTaskCountDown = new CountDownLatch(corePoolSize);
        List<WarnUpTask> warnUpTasks = IntStream.range(0, corePoolSize)
                .mapToObj((idx) -> new WarnUpTask(warnUpTaskCountDown))
                .collect(Collectors.toList());

        try {
            this.invokeAll(warnUpTasks);
        } catch (InterruptedException e) {
            throw new WarnUpException(e);
        }
    }

    private record WarnUpTask(CountDownLatch warnUpTaskCountDown) implements Callable<Long> {

        @Override
        public Long call() throws Exception {
            warnUpTaskCountDown.countDown();
            warnUpTaskCountDown.await();
            return warnUpTaskCountDown.getCount();
        }
    }

    public static class WarnUpException extends Exception {

        private WarnUpException(Throwable cause) {
            super("An exception occurred during the start of all core thread.", cause);
        }
    }
}
