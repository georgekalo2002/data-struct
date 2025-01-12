package georgekalo;

public class CacheFactory {

    /**
     * Δημιουργεί ένα αντικείμενο Cache ανάλογα με τη στρατηγική αντικατάστασης.
     * @param capacity Η χωρητικότητα της cache.
     * @param policy Η στρατηγική αντικατάστασης (LRU, MRU, LFU).
     * @return Το αντικείμενο Cache που δημιουργήθηκε.
     */
    public static <K, V> Cache<K, V> createCache(int capacity, CacheReplacementPolicy policy) {
        switch (policy) {
            case LRU:
            case MRU:
            return new LRUCache<K, V>(capacity, policy);
            case LFU:
                return new LFUCache<>(capacity); // Χρησιμοποιεί LFU
            default:
                throw new IllegalArgumentException("Unsupported cache replacement policy: " + policy);
        }
    }
}
