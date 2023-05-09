package org.jcj.ch12.test;

import org.jcj.ch11.performance.Grocery;

public class MultiThreadAddFruitTestCase extends JSR166TestCase {
    private final Grocery grocery;

    public MultiThreadAddFruitTestCase(Grocery grocery) {
        this.grocery = grocery;
        setName("test");
    }

    public void test() throws InterruptedException {
        SimpleThreadFactory simpleThreadFactory = new SimpleThreadFactory();
        Thread thread1 = simpleThreadFactory.newThread(() -> {
            for (int i = 0; i < 10000; i++) {
                grocery.addFruit(i, String.valueOf(i));
            }
        });
        Thread thread2 = simpleThreadFactory.newThread(() -> {
            for (int i = 0; i < 10000; i++) {
                grocery.addFruit(i, String.valueOf(i));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(20000, grocery.getFruits());
        assertEquals(0, grocery.getVegetables());
    }
}
