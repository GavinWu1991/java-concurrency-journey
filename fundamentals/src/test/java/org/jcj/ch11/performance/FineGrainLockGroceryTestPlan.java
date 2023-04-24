package org.jcj.ch11.performance;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class FineGrainLockGroceryTestPlan {

    @State(Scope.Benchmark)
    public static class GroceryState {
        public Grocery grocery;

        @Setup
        public void setup() {
            grocery = new FineGrainLockGrocery();
        }
    }

    @Benchmark
    public void testAddFruit(FineGrainLockGroceryTestPlan.GroceryState groceryState) {
        groceryState.grocery.addFruit(0, "Fruit- " + System.currentTimeMillis());
    }

    @Benchmark
    public void testAddVegetable(FineGrainLockGroceryTestPlan.GroceryState groceryState) {
        groceryState.grocery.addVegetable(0, "Vegetable-" + System.currentTimeMillis());
    }
}
