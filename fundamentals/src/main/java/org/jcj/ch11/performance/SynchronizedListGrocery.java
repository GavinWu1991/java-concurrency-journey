package org.jcj.ch11.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedListGrocery implements Grocery {

    private final List<String> fruits = Collections.synchronizedList(new ArrayList<>());

    private final List<String> vegetables = Collections.synchronizedList(new ArrayList<>());

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
