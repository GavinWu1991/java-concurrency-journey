package org.jcj.ch7.cancel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class ImprovedTestHarness {

    private static final Logger log = LogManager.getLogger(ImprovedTestHarness.class);

    private ImprovedTestHarness() {
        throw new UnsupportedOperationException();
    }

    public static long timeTasks(int nThreads, int timeoutInSeconds, final Runnable task)
            throws InterruptedException, TimeoutException {

        final AtomicLong duration = new AtomicLong();
        final ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        try {
            doTasks(nThreads, task, executor, duration);
        } finally {
            executor.shutdown();
        }

        if (!executor.awaitTermination(timeoutInSeconds, TimeUnit.SECONDS)) {
            throw new TimeoutException();
        }
        return duration.get();
    }

    private static void doTasks(int nThreads, Runnable task, ExecutorService executor, AtomicLong duration) {
        final AtomicLong start = new AtomicLong();

        final CyclicBarrier startBarrier = new CyclicBarrier(nThreads,
                () -> start.set(System.nanoTime()));
        final CyclicBarrier endBarrier = new CyclicBarrier(nThreads,
                () -> duration.set(System.nanoTime() - start.get()));

        for (int i = 0; i < nThreads; i++) {
            executor.execute(() -> {
                try {
                    startBarrier.await();
                    try {
                        task.run();
                    } finally {
                        endBarrier.await();
                    }
                } catch (InterruptedException | BrokenBarrierException ignored) {
                    log.warn(ignored);
                }
            });
        }
    }

}
