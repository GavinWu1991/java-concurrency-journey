package org.jcj.ch3.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ThreadLocalStatesTest extends BaseStatesTest{

    @Test
    void shouldNotDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        ThreadLocalStates threadLocalStates = new ThreadLocalStates();

        // When start all thread to modify the state instance
        modifyStatesInMultipleThread(threadLocalStates);

        // Then states value of the threadLocalStates instance should be modified which not equals to the original
        System.out.println("States in threadLocalStates instance: \n\r"+ Arrays.toString(threadLocalStates.getStates()));
        Assertions.assertArrayEquals(EXPECTED_STATES, threadLocalStates.getStates());
    }

}
