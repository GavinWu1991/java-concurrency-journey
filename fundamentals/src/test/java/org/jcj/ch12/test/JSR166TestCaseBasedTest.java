package org.jcj.ch12.test;

import junit.framework.TestSuite;
import org.jcj.ch11.performance.*;

import java.util.List;
import java.util.stream.Stream;

public class JSR166TestCaseBasedTest extends JSR166TestCase {

    static List<TestSuite> suites() {
        return Stream.of(CopyOnWriteGrocery.class, FineGrainLockGrocery.class, SynchronizedGrocery.class, SynchronizedListGrocery.class)
                .map(clz -> {
                    TestSuite nestedSuite = new TestSuite();
                    try {
                        nestedSuite.addTest(new SingleThreadAddFruitTestCase(clz.getDeclaredConstructor().newInstance()));
                        nestedSuite.addTest(new SingleThreadAddVegetableTestCase(clz.getDeclaredConstructor().newInstance()));
                        nestedSuite.addTest(new MultiThreadAddFruitTestCase(clz.getDeclaredConstructor().newInstance()));
                        nestedSuite.addTest(new MultiThreadAddVegetableTestCase(clz.getDeclaredConstructor().newInstance()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return nestedSuite;
                })
                .toList();
    }

    public static void main(String[] args) {
        for (TestSuite suite : suites()) {
            junit.textui.TestRunner.run(suite);
            System.gc();
            System.runFinalization();
        }
        System.exit(0);
    }
}
