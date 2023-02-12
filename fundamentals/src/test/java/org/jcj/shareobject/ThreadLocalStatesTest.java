package org.jcj.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ThreadLocalStatesTest extends BaseStatesTest{

    @Test
    void shouldNotDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        ThreadLocalStates threadLocalStates = new ThreadLocalStates();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> modifyStates(threadLocalStates, idx)))
                .collect(Collectors.toList());

        // When start all thread
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then states value of the threadLocalStates instance should be modified which not equals to the original
        System.out.println("States in threadLocalStates instance: \n\r"+ Arrays.toString(threadLocalStates.getStates()));
        Assertions.assertArrayEquals(EXPECTED_STATES, threadLocalStates.getStates());
    }

}
