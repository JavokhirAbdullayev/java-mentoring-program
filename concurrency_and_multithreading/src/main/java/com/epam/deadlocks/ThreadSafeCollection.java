package com.epam.deadlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadSafeCollection {

    private final List<Integer> list = new ArrayList<>();
    private final Object lock = new Object();

    public void addRandomNumber() {
        synchronized (lock) {
            var random = new Random();
            int number = random.nextInt(100);
            list.add(number);
            System.out.println("added: " + number);
        }
    }

    public int calculateSum() {
        synchronized (lock) {
            var sum = 0;
            for (var integer : list) {
                sum += integer;
            }
            return sum;
        }
    }

    public double calculateSquareRootOfSumOfSquares() {
        synchronized (lock) {
            var sumOfSquares = 0.0;
            for (var integer : list) {
                sumOfSquares += Math.pow(integer, 2);
            }
            return Math.sqrt(sumOfSquares);
        }
    }

    public static void main(String[] args) {
        var collection = new ThreadSafeCollection();

        var t1 = new Thread(() -> {
            while (true) {
                collection.addRandomNumber();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("addRandomNumber interrupted ");
                    break;
                }
            }
        });

        var t2 = new Thread(() -> {
            while (true) {
                var sum = collection.calculateSum();
                System.out.println("sum: " + sum);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("sum interrupted ");
                    break;
                }
            }
        });

        var t3 = new Thread(() -> {
            while (true) {
                var result = collection.calculateSquareRootOfSumOfSquares();
                System.out.println("square root: " + result);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("square root interrupted ");
                    break;
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        t1.interrupt();
        t2.interrupt();
        t3.interrupt();
    }
}
