package org.jcj.ch3.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CopyOnGetStatesTest extends BaseStatesTest{

    @Test
    void shouldNotDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        CopyOnGetStates copyOnGetStates = new CopyOnGetStates();

        // When start all thread to modify the state instance
        modifyStatesInMultipleThread(copyOnGetStates);

        // Then states value of the FinalStates instance should be modified which not equals to the original
        System.out.println("States in copyOnGetStates instance: \n\r"+ Arrays.toString(copyOnGetStates.getStates()));
        Assertions.assertArrayEquals(EXPECTED_STATES, copyOnGetStates.getStates());
    }

}
