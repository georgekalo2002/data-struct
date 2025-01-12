package georgekalo;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private int minFrequency;
    private final HashMap<K, Node<K, V>> keyNodeMap;
    private final HashMap<Integer, LinkedHashSet<K>> frequencyMap;

    private int hitCount = 0; // Καταμέτρηση επιτυχιών
    private int missCount = 0; // Καταμέτρηση αποτυχιών

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.keyNodeMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
    }

    @Override
    public V get(K key) {
        if (!keyNodeMap.containsKey(key)) {
            missCount++; // Αύξηση αποτυχιών
            return null; // Cache miss
        }

        hitCount++; // Αύξηση επιτυχιών
        Node<K, V> node = keyNodeMap.get(key);
        increaseFrequency(node);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }

        if (keyNodeMap.containsKey(key)) {
            Node<K, V> node = keyNodeMap.get(key);
            node.value = value;
            increaseFrequency(node);
        } else {
            if (keyNodeMap.size() == capacity) {
                evictLeastFrequentlyUsed();
            }

            Node<K, V> newNode = new Node<>(key, value);
            keyNodeMap.put(key, newNode);
            frequencyMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFrequency = 1;
        }
    }

    private void increaseFrequency(Node<K, V> node) {
        int currentFreq = node.frequency;
        frequencyMap.get(currentFreq).remove(node.key);

        if (frequencyMap.get(currentFreq).isEmpty()) {
            frequencyMap.remove(currentFreq);
            if (minFrequency == currentFreq) {
                minFrequency++;
            }
        }

        node.frequency++;
        frequencyMap.computeIfAbsent(node.frequency, k -> new LinkedHashSet<>()).add(node.key);
    }

    private void evictLeastFrequentlyUsed() {
        LinkedHashSet<K> keys = frequencyMap.get(minFrequency);
        K keyToEvict = keys.iterator().next();
        keys.remove(keyToEvict);

        if (keys.isEmpty()) {
            frequencyMap.remove(minFrequency);
        }

        keyNodeMap.remove(keyToEvict);
    }

    public void printCache() {
        System.out.print("Cache state: ");
        for (K key : keyNodeMap.keySet()) {
            System.out.print("[" + key + "=" + keyNodeMap.get(key).value + "] ");
        }
        System.out.println();
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getMissCount() {
        return missCount;
    }

    private static class Node<K, V> {
        K key;
        V value;
        int frequency;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.frequency = 1;
        }
    }
}
