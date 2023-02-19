package org.jcj.ch4.safemap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class ImprovedBySynchronizedHashMapTest extends BaseHashMapTest {


    @Test
    void shouldMissedElement_whenPutElementInMultipleThread() throws InterruptedException {
        HashMap<Integer, Integer> sharedMap = new ImprovedBySynchronizedHashMap<>();

        AtomicInteger aSequence = putElementInMultipleThread(sharedMap);

        System.out.printf("expect: [%d], current Index: [%d], actual: [%d]%n",
                BaseHashMapTest.NUMBER_OF_THREAD * BaseHashMapTest.STEP, aSequence.get(), sharedMap.size());
        Assertions.assertEquals(BaseHashMapTest.NUMBER_OF_THREAD * BaseHashMapTest.STEP, sharedMap.size());
        Assertions.assertEquals(aSequence.get(), sharedMap.size());
    }

    @Test
    void shouldHasMisOverwrittenData_whenPutElementInMultipleThread() throws InterruptedException {
        HashMap<Integer, Integer> sharedMap = new ImprovedBySynchronizedHashMap<>();

        AtomicInteger aSequence = putElementInMultipleThread(sharedMap);

        int[] misOverwrittenKeys = IntStream.range(1, aSequence.get())
                .filter(idx -> (sharedMap.containsKey(idx) && sharedMap.get(idx) != idx))
                .toArray();

        System.out.printf("expect: [%d], current Index: [%d], actual: [%d], misOverwritten: [%d]%n",
                BaseHashMapTest.NUMBER_OF_THREAD * BaseHashMapTest.STEP, aSequence.get(), sharedMap.size(), misOverwrittenKeys.length);

        Assertions.assertEquals(0, misOverwrittenKeys.length);
    }
}
