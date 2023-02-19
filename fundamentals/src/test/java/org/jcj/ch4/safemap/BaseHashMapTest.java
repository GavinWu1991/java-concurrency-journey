package org.jcj.ch4.safemap;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class BaseHashMapTest {
    protected static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() * 2;
    protected static final int STEP = 1000;

    protected void putMultipleElements(HashMap<Integer, Integer> sharedMap, AtomicInteger aSequence) {
        int localStep = STEP;
        while (localStep-- > 0) {
            sharedMap.put(aSequence.incrementAndGet(), aSequence.get());
        }
    }

    protected AtomicInteger putElementInMultipleThread(HashMap<Integer, Integer> sharedMap) throws InterruptedException {
        AtomicInteger aSequence = new AtomicInteger();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> putMultipleElements(sharedMap, aSequence)))
                .collect(Collectors.toList());

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        return aSequence;
    }
}
