package org.jcj.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class UnsafeStatesTest {

    private static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() * 2;

    private static final String[] EXPECTED_STATES = new String[] {
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY",
            "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH",
            "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
    };

    @Test
    void shouldDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        UnsafeStates unsafeStates = new UnsafeStates();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> modifyStates(unsafeStates, idx)))
                .collect(Collectors.toList());

        // When start all thread
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then states value of the UnsafeStates instance should be modified which not equals to the original
        System.out.println("States in unsafeStates instance: \n\r"+ Arrays.toString(unsafeStates.getStates()));
        Assertions.assertNotSame(EXPECTED_STATES, unsafeStates.getStates());
        // And the count of modified thread should not equal to the real number of thread
        // * because of the race condition
        Assertions.assertNotEquals(NUMBER_OF_THREAD, unsafeStates.getStates()[0].indexOf("modified by thread"));
    }

    private void modifyStates(UnsafeStates unsafeStates, int threadIdx) {
        String[] localStates = unsafeStates.getStates();
        localStates[0] = String.format("%s (modified by thread[%d])", localStates[0], threadIdx);
    }

}
