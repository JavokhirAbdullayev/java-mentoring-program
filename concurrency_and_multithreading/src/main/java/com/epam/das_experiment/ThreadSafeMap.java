package com.epam.das_experiment;

import java.util.HashMap;

public class ThreadSafeMap<K,V> {
    private final HashMap<K, V> map;

    public ThreadSafeMap() {
        map = new HashMap<>();
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized int sumValues() {
        int sum = 0;
        for (V value : map.values()) {
            sum += (Integer) value;
        }
        return sum;
    }

    public static void main(String[] args) {
        var map = new ThreadSafeMap<Integer, Integer>();

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
