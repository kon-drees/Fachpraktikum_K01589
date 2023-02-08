package de.nrw.hagen.fp1589.service;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemoryService
{
    private Map<String, Integer> sums = new HashMap<>();

    public synchronized int get(String key) {
        return sums.get(key);
    }

    public synchronized void add(String key, int val) {
        int sum = 0;

        if (sums.containsKey(key)) {
            sum = sums.get(key);
        }

        sum += val;

        sums.put(key, (Integer)sum);
    }
}