package org.jcj.ch5.performance;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class TestHarnessTest {

    static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() + 1;

    static final int STEP = 10000;

    @ParameterizedTest
    @MethodSource("tesMapPutPerformanceProvider")
    public void tesMapPutPerformance(Map<UUID, Integer> mapUnderTest)
            throws InterruptedException {

        long duration = TestHarness.timeTasks(NUMBER_OF_THREAD, () -> {
            IntStream.range(0, STEP).forEach(idx -> mapUnderTest.put(UUID.randomUUID(), idx));
        });

        System.out.printf("Performance report of put() method - Class:[%18s], Duration:[%,d] \n",
                mapUnderTest.getClass().getSimpleName(), duration);
    }

    public static Stream<Map<UUID, Integer>> tesMapPutPerformanceProvider() {
        return Stream.of(
                new ConcurrentHashMap<>(),
                new ImprovedHashMap<>(),
                Collections.synchronizedMap(new HashMap<>())
        );
    }

}
