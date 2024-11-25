package com.epam.das_experiment;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SynchronizedMapFix {
    public static void main(String[] args) {
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());

        var writer = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        });

        var reader = new Thread(() -> {
            synchronized (map) {
                var sum = 0;
                for (var value : map.values()) {
                    sum += value;
                }
                System.out.println("Sum: " + sum);
            }
        });

        writer.start();
        reader.start();
    }
}
