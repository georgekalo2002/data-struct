package georgekalo;

import java.util.Random;

public class RandomScenarioApp {
    public static void main(String[] args) {
        int totalOperations = 100000;
        int cacheCapacity = 100;

        CacheReplacementPolicy policy = CacheReplacementPolicy.MRU; // Αλλαγή σε LRU ή MRU
        Random random = new Random();

        LRUCache<Integer, Integer> cache = new LRUCache<>(cacheCapacity, policy);

        for (int i = 0; i < totalOperations; i++) {
            int key = random.nextInt(100) < 80 ? random.nextInt(20) : random.nextInt(100);
            if (cache.get(key) == null) {
                cache.put(key, random.nextInt(1000));
            }
        }

        System.out.println("Total operations: " + totalOperations);
        System.out.println("Cache Hits: " + cache.getHitCount());
        System.out.println("Cache Misses: " + cache.getMissCount());
        System.out.printf("Hit Rate: %.2f%%%n", (double) cache.getHitCount() / totalOperations * 100);
        System.out.printf("Miss Rate: %.2f%%%n", (double) cache.getMissCount() / totalOperations * 100);
    }
}
