package org.jcj.ch12.test;

import org.jcj.ch11.performance.Grocery;

public class SingleThreadAddVegetableTestCase extends JSR166TestCase {
    private final Grocery grocery;

    public SingleThreadAddVegetableTestCase(Grocery grocery) {
        this.grocery = grocery;
        setName("test");
    }

    public void test() {
        int vegetable = nine;
        int fruitExpectedSize = zero;
        for (int i = 0; i < vegetable; i++) {
            grocery.addVegetable(i, String.valueOf(i));
        }
        assertEquals(fruitExpectedSize, grocery.getFruits());
        assertEquals(vegetable, grocery.getVegetables());
    }
}
