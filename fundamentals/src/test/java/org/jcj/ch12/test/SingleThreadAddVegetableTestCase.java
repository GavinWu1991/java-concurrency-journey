package org.jcj.ch12.test;

import org.jcj.ch11.performance.Grocery;

public class SingleThreadAddVegetableTestCase extends JSR166TestCase {
    private final Grocery grocery;

    public SingleThreadAddVegetableTestCase(Grocery grocery) {
        this.grocery = grocery;
        setName("testSingleThreadAddVegetableTestCase");
    }

    public void testSingleThreadAddVegetableTestCase() {
        int vegetable = nine;
        for (int i = 0; i < vegetable; i++) {
            grocery.addVegetable(i, String.valueOf(i));
        }
        threadAssertEquals(zero.intValue(), grocery.getFruits());
        threadAssertEquals(vegetable, grocery.getVegetables());
    }
}
