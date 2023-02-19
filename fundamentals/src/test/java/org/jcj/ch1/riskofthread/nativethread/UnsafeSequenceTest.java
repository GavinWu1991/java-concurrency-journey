package org.jcj.ch1.riskofthread.nativethread;

import org.jcj.ch1.riskofthread.SequenceInvoker;
import org.jcj.ch1.riskofthread.UnsafeSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class UnsafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 60000;

    private static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() * 4;

    @Test
    void shouldRaceCondition_WhenMultipleWorkersIncreaseUnsafeSequence() throws InterruptedException {
        // Given multiple thread worker will operate same unsafeSequence instance simultaneous
        UnsafeSequence unsafeSequence = new UnsafeSequence();

        List<Thread> threads = IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj((idx) -> new Thread(() -> SequenceInvoker.increaseSequence(unsafeSequence, INCREMENT_PER_THREAD)))
                .collect(Collectors.toList());

        // When start all thread
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }

        // Then value of the safeSequence instance should be lower than the sum of the increased number of all threads
        // As the sequence is not thread safe and race condition occurred
        Assertions.assertNotEquals(INCREMENT_PER_THREAD * NUMBER_OF_THREAD, unsafeSequence.getNext());
    }
}
