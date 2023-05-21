package org.jcj.ch13.explicitlock;

import org.jcj.ch11.performance.Grocery;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class FairReentrantLockGroceryTestPlan {

    @State(Scope.Benchmark)
    public static class GroceryState {
        public Grocery grocery;

        @Setup
        public void setup() {
            grocery = new ReentrantLockGrocery(true);
        }
    }

    @Benchmark
    public void testAddFruit(GroceryState groceryState) {
        groceryState.grocery.addFruit(0, "Fruit- " + System.currentTimeMillis());
    }

    @Benchmark
    public void testAddVegetable(GroceryState groceryState) {
        groceryState.grocery.addVegetable(0, "Vegetable-" + System.currentTimeMillis());
    }
}
