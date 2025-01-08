package georgekalo;

public class App {
    public static void main(String[] args) {
        // Create an LRU cache with capacity 3
        LRUCache<Integer, String> cache = new LRUCache<>(100, CacheReplacementPolicy.LRU);


        // Add key-value pairs to the cache
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        // Access elements from the cache
        System.out.println(cache.get(1)); // Output: A (most recently used now)
        cache.put(4, "D"); // Evicts key 2 (least recently used)

        System.out.println(cache.get(2)); // Output: null (key 2 was evicted)
        System.out.println(cache.get(3)); // Output: C
        System.out.println(cache.get(4)); // Output: D
    }
}
