package org.jcj.riskofthread.nativethread;

import org.jcj.riskofthread.SequenceInvoker;
import org.jcj.riskofthread.UnsafeSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnsafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 10000;

    @Test
    void getNext() throws InterruptedException {
        // Given two thread worker will operate same unsafeSequence instance simultaneous
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        Thread t1 = new Thread(() -> SequenceInvoker.increaseSequence(unsafeSequence, INCREMENT_PER_THREAD));
        Thread t2 = new Thread(() -> SequenceInvoker.increaseSequence(unsafeSequence, INCREMENT_PER_THREAD));

        // When start all thread
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        // Then value of the safeSequence instance should be lower than the sum of the increased number of all threads
        // As the sequence is not thread safe and race condition occurred
        Assertions.assertNotEquals(INCREMENT_PER_THREAD * 2, unsafeSequence.getNext());
    }
}
