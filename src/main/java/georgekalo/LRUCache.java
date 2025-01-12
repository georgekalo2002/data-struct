package georgekalo;

import java.util.HashMap;

public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final CacheReplacementPolicy policy;
    private final HashMap<K, Node<K, V>> map;
    private final DoublyLinkedList<K, V> list;

    private int hitCount = 0;
    private int missCount = 0;

    public LRUCache(int capacity, CacheReplacementPolicy policy) {
        this.capacity = capacity;
        this.policy = policy;
        this.map = new HashMap<>();
        this.list = new DoublyLinkedList<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            missCount++;
            return null;
        }
        hitCount++;
        Node<K, V> node = map.get(key);
        if (policy == CacheReplacementPolicy.LRU) {
            list.moveToTail(node); // Μετακινεί το στοιχείο στο τέλος μόνο για LRU
           
        }
        // Στη MRU, η θέση παραμένει αμετάβλητη
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            if (policy == CacheReplacementPolicy.LRU) {
                list.moveToTail(node); // Μετακινεί το στοιχείο στο τέλος για LRU
                
            }
        } else {
            if (map.size() == capacity) {
                Node<K, V> removed;
                if (policy == CacheReplacementPolicy.LRU) {
                    removed = list.removeHead(); // Αφαιρεί το λιγότερο πρόσφατο για LRU
                } else {
                    removed = list.removeTail(); // Αφαιρεί το πιο πρόσφατο για MRU
                    
                }

                map.remove(removed.key);
            }
            Node<K, V> newNode = new Node<>(key, value);
            list.addToTail(newNode); // Προσθέτει το νέο στοιχείο στο τέλος
            map.put(key, newNode);
        }
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
        Node<K, V> prev;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoublyLinkedList<K, V> {
        private final Node<K, V> head;
        private final Node<K, V> tail;

        DoublyLinkedList() {
            this.head = new Node<>(null, null);
            this.tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
        }

        void addToTail(Node<K, V> node) {
            node.prev = tail.prev;
            node.next = tail;
            tail.prev.next = node;
            tail.prev = node;
        }

        void moveToTail(Node<K, V> node) {
            removeNode(node);
            addToTail(node);
        }

        Node<K, V> removeHead() {
            if (head.next == tail) {
                return null;
            }
            Node<K, V> first = head.next;
            removeNode(first);
            return first;
        }

        Node<K, V> removeTail() {
            if (tail.prev == head) {
                return null;
            }
            Node<K, V> last = tail.prev;
            removeNode(last);
            return last;
        }

        private void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    public void printCache() {
        Node<K, V> current = list.head.next;
        System.out.print("Cache state: ");
        while (current != list.tail) {
            System.out.print("[" + current.key + "=" + current.value + "] ");
            current = current.next;
        }
        System.out.println();
    }
}
