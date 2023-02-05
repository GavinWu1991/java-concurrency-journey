package org.jcj.riskofthread.parralleltestexecutor;

import org.jcj.riskofthread.SequenceInvoker;
import org.jcj.riskofthread.UnsafeSequence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class UnsafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 10000;

    private static final int NUMBER_OF_THREAD = 2;

    private static final UnsafeSequence unsafeSequence = new UnsafeSequence();

    @Execution(ExecutionMode.CONCURRENT)
    @TestFactory
    Collection<DynamicTest> givenMultipleWorkersIncreasedSafeSequence() {
        return IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj(UnsafeSequenceTest::buildDynamicTestWorker)
                .collect(Collectors.toList());
    }

    @AfterAll
    static void thenValueNotEqualsToIncremented() {
        Assertions.assertNotEquals(INCREMENT_PER_THREAD * NUMBER_OF_THREAD, unsafeSequence.getNext());
    }

    private static DynamicTest buildDynamicTestWorker(int workerIdx) {
        return dynamicTest("Test Worker: " + workerIdx,
                () -> SequenceInvoker.increaseSequence(unsafeSequence, INCREMENT_PER_THREAD));
    }
}
