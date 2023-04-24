package org.jcj.ch11.performance;

import net.jcip.annotations.GuardedBy;

import java.util.ArrayList;
import java.util.List;

class SynchronizedGrocery implements Grocery {

    @GuardedBy("this")
    private final List<String> fruits = new ArrayList<>();

    @GuardedBy("this")
    private final List<String> vegetables = new ArrayList<>();

    public synchronized void addFruit(int index, String fruit) {
        fruits.add(index, fruit);
    }

    public synchronized void addVegetable(int index, String vegetable) {
        vegetables.add(index, vegetable);
    }
}
