package org.jcj.ch10.deadlock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class LeftRightDeadLockDemo {

    private final static Logger log = LogManager.getLogger(LeftRightDeadLockDemo.class);

    private final Object right = new Object();

    private final Object left = new Object();

    public static void main(String[] args) {
        LeftRightDeadLockDemo testInstance = new LeftRightDeadLockDemo();
        testInstance.execute();
    }

    public void execute() {
        List<Callable<Object>> tasks = List.of(this::rightToLeft, this::leftToRight);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            log.info("One of task completed: {}", executor.invokeAll(tasks).get(0).get());
        } catch (ExecutionException | InterruptedException exception) {
            log.info("Error occurred", exception);
        }
    }

    private Object rightToLeft() {
        Thread.currentThread().setName("Task:rightToLeft");
        synchronized (right) {
            log.info("Locked Right");
            synchronized (left) {
                log.info("Locked Right to Left");
                return left;
            }
        }
    }

    private Object leftToRight() {
        Thread.currentThread().setName("Task:leftToRight");
        synchronized (left) {
            log.info("Locked Left");
            synchronized (right) {
                log.info("Locked Left to Right");
                return right;
            }
        }
    }
}
