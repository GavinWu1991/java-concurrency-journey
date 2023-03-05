package org.jcj.ch5.performance;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

class TestHarnessTest {

    @ParameterizedTest
    @ValueSource(ints = {100, 1000, 10000, 100000, 200000})
    public void tesMapPutPerformance(int steps) {
        Map<String, Long> performanceResult = PerformanceTestFixture.hashMapInstanceProvider()
                .collect(Collectors.toMap(
                        mapUnderTest -> mapUnderTest.getClass().getSimpleName(),
                        mapUnderTest -> multiInvokeTestHarness(idx -> mapUnderTest.put(UUID.randomUUID(), idx), steps
                        )));

        System.out.printf("\n\rPerformance report of put()x[%,8d]\n\r", steps);

        performanceResult.forEach((key, value) ->
                System.out.printf("Performance report of put()x[%,8d] - Class:[%18s], Duration:[%,16d] \n",
                        steps, key, value));
    }

    private static long multiInvokeTestHarness(IntConsumer testFn, int steps) {
        try {
            return TestHarness.timeTasks(
                    PerformanceTestFixture.NUMBER_OF_THREAD,
                    () -> PerformanceTestFixture.multiInvoke(testFn, steps));
        } catch (InterruptedException skipped) {
            throw new RuntimeException(skipped);
        }
    }

}
