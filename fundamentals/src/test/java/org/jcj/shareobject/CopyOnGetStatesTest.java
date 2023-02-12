package org.jcj.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CopyOnGetStatesTest extends BaseStatesTest{

    @Test
    void shouldNotsDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        CopyOnGetStates copyOnGetStates = new CopyOnGetStates();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> modifyStates(copyOnGetStates, idx)))
                .collect(Collectors.toList());

        // When start all thread
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then states value of the FinalStates instance should be modified which not equals to the original
        System.out.println("States in copyOnGetStates instance: \n\r"+ Arrays.toString(copyOnGetStates.getStates()));
        Assertions.assertArrayEquals(EXPECTED_STATES, copyOnGetStates.getStates());
    }
}
