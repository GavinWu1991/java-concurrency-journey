package org.jcj.ch11.performance;

import net.jcip.annotations.GuardedBy;

import java.util.ArrayList;
import java.util.List;

public class FineGrainLockGrocery implements Grocery {
    @GuardedBy("fruits")
    private final List<String> fruits = new ArrayList<>();

    @GuardedBy("vegetables")
    private final List<String> vegetables = new ArrayList<>();

    public void addFruit(int index, String fruit) {
        synchronized (fruits) {
            fruits.add(index, fruit);
        }
    }

    public void addVegetable(int index, String vegetable) {
        synchronized (vegetables) {
            vegetables.add(index, vegetable);
        }
    }

    @Override
    public int getFruits() {
        synchronized (fruits) {
            return fruits.size();
        }
    }

    @Override
    public int getVegetables() {
        synchronized (vegetables) {
            return vegetables.size();
        }
    }
}
