package georgekalo;

import static org.junit.Assert.*;
import org.junit.Test;

public class LRUCacheTest {

    @Test
    public void testLRUBasicFunctionality() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        // Προσθήκη 1, 2, 3
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        assertEquals("A", cache.get(1)); // Το 1 πρέπει να μετακινηθεί στο τέλος

        // Προσθήκη 4
        cache.put(4, "D"); // Το 2 πρέπει να αφαιρεθεί
        assertNull("Key 2 should have been evicted", cache.get(2));
        assertEquals("D", cache.get(4));
    }

    @Test
    public void testMRUBasicFunctionality() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.MRU);

        // Προσθήκη 1, 2, 3
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        assertEquals("C", cache.get(3)); // Το 3 παραμένει πιο πρόσφατο

        // Προσθήκη 4
        cache.put(4, "D"); // Το 3 πρέπει να αφαιρεθεί
        assertNull("Key 3 should have been evicted", cache.get(3));
        assertEquals("D", cache.get(4));
    }

    @Test
    public void testLFUBasicFunctionality() {
        LFUCache<Integer, String> cache = new LFUCache<>(3);

        // Προσθήκη 1, 2, 3
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.get(1); // Αυξάνει τη συχνότητα του 1
        cache.get(2); // Αυξάνει τη συχνότητα του 2

        // Προσθήκη 4
        cache.put(4, "D"); // Το 3 πρέπει να αφαιρεθεί (λιγότερο χρησιμοποιούμενο)
        assertNull("Key 3 should have been evicted", cache.get(3));
        assertEquals("D", cache.get(4));
    }

    @Test
    public void testEdgeCases() {
        LRUCache<Integer, String> cache = new LRUCache<>(2, CacheReplacementPolicy.LRU);

        // Προσθήκη 1
        cache.put(1, "A");
        assertEquals("A", cache.get(1));

        // Προσθήκη 2
        cache.put(2, "B");

        // Προσθήκη 3
        cache.put(3, "C"); // Το 1 πρέπει να αφαιρεθεί
        assertNull("Key 1 should have been evicted", cache.get(1));
        assertEquals("B", cache.get(2));
        assertEquals("C", cache.get(3));
    }

    @Test
    public void testStressTestLFU() {
        int capacity = 5;
        LFUCache<Integer, Integer> cache = new LFUCache<>(capacity);

        // Προσθήκη μεγάλου όγκου δεδομένων
        for (int i = 1; i <= 10; i++) {
            cache.put(i, i);
        }

        // Έλεγχος αν παραμένουν μόνο τα τελευταία 5 στοιχεία
        for (int i = 1; i <= 5; i++) {
            assertNull("Key " + i + " should have been evicted", cache.get(i));
        }
        for (int i = 6; i <= 10; i++) {
            assertEquals(Integer.valueOf(i), cache.get(i));
        }
    }

    @Test
    public void testStressTestLRU() {
        int capacity = 5;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity, CacheReplacementPolicy.LRU);

        // Προσθήκη μεγάλου όγκου δεδομένων
        for (int i = 1; i <= 10; i++) {
            cache.put(i, i);
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
