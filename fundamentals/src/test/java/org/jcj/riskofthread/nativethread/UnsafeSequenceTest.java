package org.jcj.riskofthread.nativethread;

import org.jcj.riskofthread.SequenceInvoker;
import org.jcj.riskofthread.UnsafeSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnsafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 10000;

    @Test
    void getNext() throws InterruptedException {
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        Thread t1 = new Thread(() -> SequenceInvoker.increaseSequence(unsafeSequence, INCREMENT_PER_THREAD));
        Thread t2 = new Thread(() -> SequenceInvoker.increaseSequence(unsafeSequence, INCREMENT_PER_THREAD));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        Assertions.assertNotEquals(INCREMENT_PER_THREAD * 2, unsafeSequence.getNext());
    }
}
