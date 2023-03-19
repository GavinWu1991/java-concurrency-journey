package org.jcj.ch7.cancel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.jcj.ch5.performance.PerformanceTestFixture.NUMBER_OF_THREAD;
import static org.junit.jupiter.api.Assertions.*;

class ImprovedTestHarnessTest {

    private final static Logger log = LogManager.getLogger(ImprovedTestHarnessTest.class);

    @Test
    void shouldCancelled_whenExecuteLongTimeTask() {
        long start = System.nanoTime();

        assertThrows(TimeoutException.class, () -> ImprovedTestHarness
                .timeTasks(NUMBER_OF_THREAD, 4, new TimeoutTask(60)));

        long duration = System.nanoTime() - start;
        log.info("Completed all tasks within [{}] mill seconds",
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));

        assertEquals(4, TimeUnit.NANOSECONDS.toSeconds(duration));
    }

    @Test
    void shouldCancelled_whenExecuteInfiniteLoopTask() {
        long start = System.nanoTime();

        assertThrows(TimeoutException.class, () -> ImprovedTestHarness
                .timeTasks(NUMBER_OF_THREAD, 4, new InfiniteLoopTask()));

        long duration = System.nanoTime() - start;
        log.info("Completed all tasks within [{}] mill seconds",
                TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));

        assertEquals(4, TimeUnit.NANOSECONDS.toSeconds(duration));
    }

    @Test
    void shouldCompleted_whenExecuteInstantTimeTask() {
        long duration = assertDoesNotThrow(() -> ImprovedTestHarness
                .timeTasks(NUMBER_OF_THREAD, 4, new TimeoutTask(2)));

        log.info("Completed all tasks within [{}] mill seconds",
                TimeUnit.NANOSECONDS.toMillis(duration));

        assertEquals(2, TimeUnit.NANOSECONDS.toSeconds(duration));

    }

    private static class TimeoutTask implements Runnable {

        private final int timeout;

        private TimeoutTask(int timeout) {
            this.timeout = timeout;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException ignored) {
            }
        }
    }


    private static class InfiniteLoopTask implements Runnable {

        @Override
        public void run() {
            int meaninglessCounter = 0;
            while (true) {
                meaninglessCounter++;
                meaninglessCounter--;
            }
        }
    }
}
