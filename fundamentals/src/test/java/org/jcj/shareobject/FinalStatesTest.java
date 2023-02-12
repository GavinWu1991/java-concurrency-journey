package org.jcj.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FinalStatesTest extends BaseStatesTest {

    @Test
    void shouldDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        FinalStates finalStates = new FinalStates();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> modifyStates(finalStates, idx)))
                .collect(Collectors.toList());

        // When start all thread
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then states value of the FinalStates instance should be modified which not equals to the original
        System.out.println("States in unsafeStates instance: \n\r"+ Arrays.toString(finalStates.getStates()));
        Assertions.assertNotSame(EXPECTED_STATES, finalStates.getStates());
        // And the count of modified thread should not equal to the real number of thread
        // * because of the race condition
        Assertions.assertNotEquals(NUMBER_OF_THREAD, finalStates.getStates()[0].indexOf("modified by thread"));
    }

}
