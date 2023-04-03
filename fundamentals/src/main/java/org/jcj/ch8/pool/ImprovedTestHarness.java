package org.jcj.ch8.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class ImprovedTestHarness {

    private static final Logger log = LogManager.getLogger(ImprovedTestHarness.class);

    private ImprovedTestHarness() {
        throw new UnsupportedOperationException();
    }

    public static long timeTasks(int nThreads, boolean shouldStartAllCoreThreads, final Runnable task)
            throws InterruptedException, BrokenBarrierException, TimingThreadPool.WarnUpException {

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(nThreads + 1,
                () -> log.debug("All test thread [{}] reach barrier.", nThreads));

        final ThreadPoolExecutor executor = new TimingThreadPool(nThreads, shouldStartAllCoreThreads);

        for (int i = 0; i < nThreads; i++) {
            executor.execute(() -> {
                try {
                    log.debug("Test threads [{}/{}] reached entry barrier.",
                            cyclicBarrier.getNumberWaiting(), cyclicBarrier.getParties());

                    cyclicBarrier.await();
                    try {
                        task.run();
                    } finally {
                        log.debug("Test threads [{}/{}] reached exit barrier.",
                                cyclicBarrier.getNumberWaiting(), cyclicBarrier.getParties());
                        cyclicBarrier.await();
                    }
                } catch (InterruptedException | BrokenBarrierException ignored) {
                }
            });
        }

        cyclicBarrier.await();
        long start = System.nanoTime();

        cyclicBarrier.reset();
        cyclicBarrier.await();
        long end = System.nanoTime();

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        return end - start;
    }
}
