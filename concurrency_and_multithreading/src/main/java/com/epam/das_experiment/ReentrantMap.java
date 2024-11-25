package com.epam.das_experiment;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantMap<K, V> {

    private final HashMap<K, V> map;
    private final Lock lock;

    public ReentrantMap() {
        lock = new ReentrantLock();
        map = new HashMap<>();
    }

    public void put(K key, V value) {
        if (lock.tryLock()) {
            try {
                map.put(key, value);
            } finally {
                lock.unlock();
            }
        }
    }

    public V get(K key) {
        return map.get(key);
    }

    public int sumValues() {
        var sum = 0;
        if (lock.tryLock()) {
            try {
                sum = map.values().stream().mapToInt(v -> (Integer) v).sum();
            } finally {
                lock.unlock();
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        var map = new ReentrantMap<Integer, Integer>();

        var reader = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        });

        var writer = new Thread(()-> {
            var sum = map.sumValues();
            System.out.println("Sum: " + sum);
        });

        reader.start();
        writer.start();
    }
}
