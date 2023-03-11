package org.jcj.ch5.performance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PerformanceTestFixture {

    // use N cpu + 1 thread to simulate the concurrency
    public static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() + 1;

    public static void multiInvoke(IntConsumer testFn, int steps) {
        IntStream.range(0, steps)
                .forEach(testFn);
    }

    public static Stream<Map<UUID, Integer>> hashMapInstanceProvider() {
        return Stream.of(
                new ConcurrentHashMap<>(),
                new ImprovedHashMap<>(),
                Collections.synchronizedMap(new HashMap<>())
        );
    }
}
