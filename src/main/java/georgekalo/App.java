package georgekalo;

public class App {
    public static void main(String[] args) {
        
        LRUCache<Integer, String> cache = new LRUCache<>(100, CacheReplacementPolicy.LRU);


        
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        
        System.out.println(cache.get(1)); 
        cache.put(4, "D"); 

        System.out.println(cache.get(2)); 
        System.out.println(cache.get(3)); 
        System.out.println(cache.get(4)); 
    }
}
