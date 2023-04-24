package org.jcj.ch11.performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class SynchronizedListGroceryTestPlan {

    @State(Scope.Benchmark)
    public static class GroceryState {
        public Grocery grocery;

        @Setup
        public void setup() {
            grocery = new SynchronizedListGrocery();
        }
    }

    @Benchmark
    public void testAddFruit(SynchronizedListGroceryTestPlan.GroceryState groceryState) {
        groceryState.grocery.addFruit(0, "Fruit- " + System.currentTimeMillis());
    }

    @Benchmark
    public void testAddVegetable(SynchronizedListGroceryTestPlan.GroceryState groceryState) {
        groceryState.grocery.addVegetable(0, "Vegetable-" + System.currentTimeMillis());
    }
}
