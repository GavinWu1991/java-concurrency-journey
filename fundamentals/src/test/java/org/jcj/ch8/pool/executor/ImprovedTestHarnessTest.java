package org.jcj.ch8.pool.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jcj.ch5.performance.PerformanceTestFixture;
import org.jcj.ch8.pool.ImprovedTestHarness;
import org.jcj.ch8.pool.TimingThreadPool;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

class ImprovedTestHarnessTest {

    private final static Logger log = LogManager.getLogger(ImprovedTestHarnessTest.class);

    @ParameterizedTest
    @ValueSource(ints = {100, 1000, 10000, 100000})
    public void tesMapPutPerformanceWithoutWarnUp(int steps) {
        Map<String, Long> performanceResult = PerformanceTestFixture.hashMapInstanceProvider()
                .collect(Collectors.toMap(
                        mapUnderTest -> mapUnderTest.getClass().getSimpleName(),
                        mapUnderTest -> multiInvokeTestHarness(idx -> mapUnderTest.put(UUID.randomUUID(), idx),
                                steps,
                                false)));

        log.info(String.format("Performance report of put()x[%,8d]", steps));

        performanceResult.forEach((key, value) ->
                log.info(String.format("Performance report of put()x[%,8d] - Class:[%18s], Duration:[%,16d]",
                        steps, key, value)));
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 1000, 10000, 100000})
    public void tesMapPutPerformanceWithWarnUp(int steps) {
        Map<String, Long> performanceResult = PerformanceTestFixture.hashMapInstanceProvider()
                .collect(Collectors.toMap(
                        mapUnderTest -> mapUnderTest.getClass().getSimpleName(),
                        mapUnderTest -> multiInvokeTestHarness(idx -> mapUnderTest.put(UUID.randomUUID(), idx),
                                steps,
                                true)));

        log.info(String.format("Performance report of put()x[%,8d] with warn up", steps));

        performanceResult.forEach((key, value) ->
                log.info(String.format("Performance report of put()x[%,8d] with warn up - Class:[%18s], Duration:[%,16d]",
                        steps, key, value)));
    }

    private static long multiInvokeTestHarness(IntConsumer testFn, int steps, boolean shouldStartAllCoreThreads) {
        try {
            return ImprovedTestHarness.timeTasks(
                    PerformanceTestFixture.NUMBER_OF_THREAD,
                    shouldStartAllCoreThreads,
                    () -> PerformanceTestFixture.multiInvoke(testFn, steps));
        } catch (BrokenBarrierException | InterruptedException | TimingThreadPool.WarnUpException skipped) {
            throw new RuntimeException(skipped);
        }
    }

}
