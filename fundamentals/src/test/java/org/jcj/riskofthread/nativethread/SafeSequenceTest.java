package org.jcj.riskofthread.nativethread;

import org.jcj.riskofthread.SafeSequence;
import org.jcj.riskofthread.SequenceInvoker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 10000;

    @Test
    void getNext() throws InterruptedException {
        // Given two thread worker will operate same safeSequence instance simultaneous
        SafeSequence safeSequence = new SafeSequence();
        Thread t1 = new Thread(() -> SequenceInvoker.increaseSequence(safeSequence, INCREMENT_PER_THREAD));
        Thread t2 = new Thread(() -> SequenceInvoker.increaseSequence(safeSequence, INCREMENT_PER_THREAD));

        // When start all thread
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Then value of the safeSequence instance should be equal to the sum of the increased number of all threads
        Assertions.assertEquals(INCREMENT_PER_THREAD * 2, safeSequence.getNext());
    }

}
