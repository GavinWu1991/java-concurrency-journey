package org.jcj.ch6.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jcj.ch5.performance.PerformanceTestFixture;
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
    public void tesMapPutPerformance(int steps) {
        Map<String, Long> performanceResult = PerformanceTestFixture.hashMapInstanceProvider()
                .collect(Collectors.toMap(
                        mapUnderTest -> mapUnderTest.getClass().getSimpleName(),
                        mapUnderTest -> multiInvokeTestHarness(idx -> mapUnderTest.put(UUID.randomUUID(), idx), steps
                        )));

        log.info(String.format("Performance report of put()x[%,8d]", steps));

        performanceResult.forEach((key, value) ->
                log.info(String.format("Performance report of put()x[%,8d] - Class:[%18s], Duration:[%,16d]",
                        steps, key, value)));
    }

    private static long multiInvokeTestHarness(IntConsumer testFn, int steps) {
        try {
            return ImprovedTestHarness.timeTasks(
                    PerformanceTestFixture.NUMBER_OF_THREAD,
                    () -> PerformanceTestFixture.multiInvoke(testFn, steps));
        } catch (BrokenBarrierException | InterruptedException skipped) {
            throw new RuntimeException(skipped);
        }
    }

}
