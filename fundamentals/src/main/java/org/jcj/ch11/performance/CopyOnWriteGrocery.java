package org.jcj.ch11.performance;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class CopyOnWriteGrocery implements Grocery {
    private final List<String> fruits = new CopyOnWriteArrayList<>();
    private final List<String> vegetables = new CopyOnWriteArrayList<>();

    public void addFruit(int index, String fruit) {
        fruits.add(index, fruit);
    }

    public void addVegetable(int index, String vegetable) {
        vegetables.add(index, vegetable);
    }
}
