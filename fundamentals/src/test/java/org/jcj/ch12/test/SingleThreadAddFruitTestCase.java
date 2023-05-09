package org.jcj.ch12.test;

import org.jcj.ch11.performance.Grocery;

public class SingleThreadAddFruitTestCase extends JSR166TestCase {
    private final Grocery grocery;

    public SingleThreadAddFruitTestCase(Grocery grocery) {
        this.grocery = grocery;
        setName("testSingleThreadAddFruitTestCase");
    }

    public void testSingleThreadAddFruitTestCase() {
        int fruitSize = nine;
        for (int i = 0; i < fruitSize; i++) {
            grocery.addFruit(i, String.valueOf(i));
        }
        assertEquals(fruitSize, grocery.getFruits());
        assertEquals(zero.intValue(), grocery.getVegetables());
    }
}
