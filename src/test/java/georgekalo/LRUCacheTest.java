package georgekalo;

import static org.junit.Assert.*;
import org.junit.Test;

public class LRUCacheTest {

    @Test
    public void testBasicFunctionality() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        // Προσθήκη 1, 2, 3
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println("Cache state after adding 1, 2, 3:");
        cache.printCache();

        // Ανάκτηση του 1
        assertEquals("A", cache.get(1)); // Το 1 πρέπει να μετακινηθεί στο τέλος
        System.out.println("Cache state after accessing key 1:");
        cache.printCache();

        // Προσθήκη 4
        cache.put(4, "D"); // Το 2 πρέπει να αφαιρεθεί
        System.out.println("Cache state after adding key 4:");
        cache.printCache();

        // Έλεγχος αν το 2 αφαιρέθηκε
        assertNull("Key 2 should have been evicted", cache.get(2));

        // Έλεγχος αν το 4 είναι στη cache
        assertEquals("D", cache.get(4));
        System.out.println("Cache state after accessing key 4:");
        cache.printCache();
    }

    @Test
    public void testEdgeCases() {
        LRUCache<Integer, String> cache = new LRUCache<>(2, CacheReplacementPolicy.LRU);

        // Προσθήκη 1
        cache.put(1, "A");
        System.out.println("Cache state after adding key 1:");
        cache.printCache();
        assertEquals("A", cache.get(1));

        // Προσθήκη 2
        cache.put(2, "B");
        System.out.println("Cache state after adding key 2:");
        cache.printCache();

        // Προσθήκη 3
        cache.put(3, "C"); // Το 1 πρέπει να αφαιρεθεί
        System.out.println("Cache state after adding key 3:");
        cache.printCache();

        // Έλεγχος αν το 1 αφαιρέθηκε
        assertNull("Key 1 should have been evicted", cache.get(1));
        assertEquals("B", cache.get(2));
        assertEquals("C", cache.get(3));
    }

    @Test
    public void testStressTest() {
        int capacity = 5;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity, CacheReplacementPolicy.LRU);

        // Προσθήκη μεγάλου όγκου δεδομένων
        for (int i = 1; i <= 10; i++) {
            cache.put(i, i);
            if (i > capacity) {
                System.out.println("Cache state after adding key " + i + ":");
                cache.printCache();
            }
        }

        // Έλεγχος αν παραμένουν μόνο τα τελευταία 5 στοιχεία
        for (int i = 1; i <= 5; i++) {
            assertNull("Key " + i + " should have been evicted", cache.get(i));
        }
        for (int i = 6; i <= 10; i++) {
            assertEquals(Integer.valueOf(i), cache.get(i));
        }
    }
}
