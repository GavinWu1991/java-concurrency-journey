package org.jcj.shareobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class UnsafeStatesTest extends BaseStatesTest {

    @Test
    void shouldDataEscape_WhenAccessStateField() throws InterruptedException {
        // given an instance of UnsafeStates created and shared to other threads
        UnsafeStates unsafeStates = new UnsafeStates();

        // When start all thread to modify the state instance
        modifyStatesInMultipleThread(unsafeStates);

        // Then states value of the UnsafeStates instance should be modified which not equals to the original
        System.out.println("States in unsafeStates instance: \n\r"+ Arrays.toString(unsafeStates.getStates()));
        Assertions.assertNotSame(EXPECTED_STATES, unsafeStates.getStates());
        // And the count of modified thread should not equal to the real number of thread
        // * because of the race condition
        Assertions.assertNotEquals(NUMBER_OF_THREAD, unsafeStates.getStates()[0].indexOf("modified by thread"));
    }

}
