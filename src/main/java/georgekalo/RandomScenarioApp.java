package georgekalo;

import java.util.HashSet;
import java.util.Random;

public class RandomScenarioApp {
    public static void main(String[] args) {
        int totalOperations = 100000; // Συνολικές λειτουργίες
        int cacheCapacity = 5; // Χωρητικότητα της cache
        CacheReplacementPolicy policy = CacheReplacementPolicy.LFU; // LFU, LRU, MRU
        Random random = new Random();

        // Δημιουργία της cache μέσω της CacheFactory
        Cache<Integer, Integer> cache = CacheFactory.createCache(cacheCapacity, policy);

        // Σύνολο για αποθήκευση μοναδικών κλειδιών για έλεγχο τυχαιότητας
        HashSet<Integer> uniqueKeys = new HashSet<>();

        // Ενέργειες στην cache
        for (int i = 0; i < totalOperations; i++) {
            int key;
            if (random.nextInt(100) < 80) { // 80% πιθανότητα
                key = random.nextInt(20); // Συχνά χρησιμοποιούμενα κλειδιά
            } else {
                key = random.nextInt(100); // Λιγότερο συχνά χρησιμοποιούμενα κλειδιά
            }

            // Εισαγωγή του key στο σύνολο για έλεγχο τυχαιότητας
            uniqueKeys.add(key);

            // Εκτέλεση λειτουργίας στην cache
            if (cache.get(key) == null) {
                int value = random.nextInt(1000); // Τυχαία τιμή
                cache.put(key, value);
            }

            // Κάθε 10.000 λειτουργίες εκτυπώνουμε πληροφορίες
            if (i % 10000 == 0) {
                System.out.println("Operation " + i);
                System.out.println("Unique keys so far: " + uniqueKeys.size());
                if (cache instanceof LRUCache) {
                    ((LRUCache<Integer, Integer>) cache).printCache(); // Εκτύπωση κατάστασης για LRU
                } else if (cache instanceof LFUCache) {
                    ((LFUCache<Integer, Integer>) cache).printCache(); // Εκτύπωση κατάστασης για LFU
                }
            }
        }

        // Εκτύπωση τελικών αποτελεσμάτων
        System.out.println("Total operations: " + totalOperations);
        if (cache instanceof LRUCache) {
            LRUCache<Integer, Integer> lruCache = (LRUCache<Integer, Integer>) cache;
            System.out.println("Cache Hits: " + lruCache.getHitCount());
            System.out.println("Cache Misses: " + lruCache.getMissCount());
            System.out.printf("Hit Rate: %.2f%%%n", (double) lruCache.getHitCount() / totalOperations * 100);
            System.out.printf("Miss Rate: %.2f%%%n", (double) lruCache.getMissCount() / totalOperations * 100);
        } else if (cache instanceof LFUCache) {
            LFUCache<Integer, Integer> lfuCache = (LFUCache<Integer, Integer>) cache;
            System.out.println("Cache Hits: " + lfuCache.getHitCount());
            System.out.println("Cache Misses: " + lfuCache.getMissCount());
            System.out.printf("Hit Rate: %.2f%%%n", (double) lfuCache.getHitCount() / totalOperations * 100);
            System.out.printf("Miss Rate: %.2f%%%n", (double) lfuCache.getMissCount() / totalOperations * 100);
        }

        // Έλεγχος τυχαιότητας
        System.out.println("Total unique keys generated: " + uniqueKeys.size());
    }
}
