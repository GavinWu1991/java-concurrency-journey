package org.jcj.ch3.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class FinalStatesTest extends BaseStatesTest {

    @Test
    void shouldDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        FinalStates finalStates = new FinalStates();

        // When start all thread to modify the state instance
        modifyStatesInMultipleThread(finalStates);

        // Then states value of the FinalStates instance should be modified which not equals to the original
        System.out.println("States in finalStates instance: \n\r" + Arrays.toString(finalStates.getStates()));
        Assertions.assertNotSame(EXPECTED_STATES, finalStates.getStates());
        // And the count of modified thread should not equal to the real number of thread
        // * because of the race condition
        Assertions.assertNotEquals(NUMBER_OF_THREAD, finalStates.getStates()[0].indexOf("modified by thread"));
    }

}
