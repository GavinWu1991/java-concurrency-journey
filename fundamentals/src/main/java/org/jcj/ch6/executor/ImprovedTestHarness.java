package org.jcj.ch6.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImprovedTestHarness {

    private final static Logger log = LogManager.getLogger(ImprovedTestHarness.class);

    private ImprovedTestHarness() {
        throw new UnsupportedOperationException();
    }

    public static long timeTasks(int nThreads, final Runnable task)
            throws InterruptedException, BrokenBarrierException {

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(nThreads + 1, () -> {
            log.debug("All test thread [{}] reach barrier.", nThreads);
        });

        final Executor executor = Executors.newFixedThreadPool(nThreads);

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
        return end - start;
    }
}
