package org.jcj.riskofthread.nativethread;

import org.jcj.riskofthread.SafeSequence;
import org.jcj.riskofthread.SequenceInvoker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class SafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 60000;

    private static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() * 4;

    @Test
    void shouldNotRaceCondition_WhenMultipleWorkersIncreaseSafeSequence() throws InterruptedException {
        // Given two thread worker will operate same safeSequence instance simultaneous
        SafeSequence safeSequence = new SafeSequence();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> SequenceInvoker.increaseSequence(safeSequence, INCREMENT_PER_THREAD)))
                .collect(Collectors.toList());

        // When start all thread
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then value of the safeSequence instance should be equal to the sum of the increased number of all threads
        Assertions.assertEquals(INCREMENT_PER_THREAD * NUMBER_OF_THREAD, safeSequence.getNext());
    }

}
