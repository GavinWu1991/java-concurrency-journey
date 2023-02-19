package org.jcj.ch1.riskofthread.parralleltestexecutor;

import org.jcj.ch1.riskofthread.SafeSequence;
import org.jcj.ch1.riskofthread.SequenceInvoker;
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

class SafeSequenceTest {

    private static final int INCREMENT_PER_THREAD = 60000;

    private static final int NUMBER_OF_THREAD = Runtime.getRuntime().availableProcessors() * 4;

    private static final SafeSequence safeSequence = new SafeSequence();


    @Execution(ExecutionMode.CONCURRENT)
    @TestFactory
    Collection<DynamicTest> givenMultipleWorkersIncreaseSafeSequence() {
        return IntStream
                .range(0, NUMBER_OF_THREAD)
                .mapToObj(SafeSequenceTest::buildDynamicTestWorker)
                .collect(Collectors.toList());
    }

    @AfterAll
    static void thenValueEqualsToIncremented() {
        Assertions.assertEquals(INCREMENT_PER_THREAD * NUMBER_OF_THREAD, safeSequence.getNext());
    }

    private static DynamicTest buildDynamicTestWorker(int workerIdx) {
        return dynamicTest("Test Worker: " + workerIdx,
                () -> SequenceInvoker.increaseSequence(safeSequence, INCREMENT_PER_THREAD));
    }

}
