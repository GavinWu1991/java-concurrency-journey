package org.jcj.ch13.explicitlock;

import net.jcip.annotations.GuardedBy;
import org.jcj.ch11.performance.Grocery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockGrocery implements Grocery {

    @GuardedBy("fruitLock")
    private final List<String> fruits = new ArrayList<>();

    @GuardedBy("vegetableLock")
    private final List<String> vegetables = new ArrayList<>();
    private final ReentrantLock fruitLock;
    private final ReentrantLock vegetableLock;

    public ReentrantLockGrocery(boolean isFair) {
        this.fruitLock = new ReentrantLock(isFair);
        this.vegetableLock = new ReentrantLock(isFair);
    }

    @Override
    public void addFruit(int index, String fruit) {
        fruitLock.lock();
        try {
            fruits.add(index, fruit);
        } finally {
            fruitLock.unlock();
        }
    }

    @Override
    public void addVegetable(int index, String vegetable) {
        vegetableLock.lock();
        try {
            vegetables.add(index, vegetable);
        } finally {
            vegetableLock.unlock();
        }
    }

    @Override
    public int getFruits() {
        fruitLock.lock();
        try {
            return fruits.size();
        } finally {
            fruitLock.unlock();
        }
    }

    @Override
    public int getVegetables() {
        vegetableLock.lock();
        try {
            return vegetables.size();
        } finally {
            vegetableLock.unlock();
        }
    }
}
