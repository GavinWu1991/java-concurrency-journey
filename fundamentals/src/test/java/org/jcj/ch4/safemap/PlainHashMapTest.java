package org.jcj.ch4.safemap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PlainHashMapTest extends BaseHashMapTest {

    @Test
    void shouldMissedElement_whenPutElementInMultipleThread() throws InterruptedException {
        HashMap<Integer, Integer> sharedMap = new HashMap<>();

        AtomicInteger aSequence = putElementInMultipleThread(sharedMap);

        System.out.printf("expect: [%d], current Index: [%d], actual: [%d]%n",
                BaseHashMapTest.NUMBER_OF_THREAD * BaseHashMapTest.STEP, aSequence.get(), sharedMap.size());
        Assertions.assertNotEquals(BaseHashMapTest.NUMBER_OF_THREAD * BaseHashMapTest.STEP, sharedMap.size());
        Assertions.assertNotEquals(aSequence.get(), sharedMap.size());
    }

    @Test
    void shouldHasMisOverwrittenData_whenPutElementInMultipleThread() throws InterruptedException {
        HashMap<Integer, Integer> sharedMap = new HashMap<>();

        AtomicInteger aSequence = putElementInMultipleThread(sharedMap);

        // other than missed element, some elements will be placed at wrong key due to the race condition
        int[] misOverwrittenKeys = IntStream.range(1, aSequence.get())
                .filter(idx -> (sharedMap.containsKey(idx) && sharedMap.get(idx) != idx))
                .toArray();

        System.out.printf("expect: [%d], current Index: [%d], actual: [%d], misOverwritten: [%d]%n",
                BaseHashMapTest.NUMBER_OF_THREAD * BaseHashMapTest.STEP, aSequence.get(), sharedMap.size(), misOverwrittenKeys.length);

        Assertions.assertTrue(misOverwrittenKeys.length > 0);
    }
}
