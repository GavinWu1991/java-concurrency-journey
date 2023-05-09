package org.jcj.ch11.performance;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteGrocery implements Grocery {
    private final List<String> fruits = new CopyOnWriteArrayList<>();
    private final List<String> vegetables = new CopyOnWriteArrayList<>();

    public void addFruit(int index, String fruit) {
        fruits.add(index, fruit);
    }

    public void addVegetable(int index, String vegetable) {
        vegetables.add(index, vegetable);
    }

    @Override
    public int getFruits() {
        return fruits.size();
    }

    @Override
    public int getVegetables() {
        return vegetables.size();
    }
}
