package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    // do I need to resize ArrayMap.keys and values???
    private static final int DEFAULT_SIZE = 4;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;
    private HashSet<K> keyset;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        // buckets = (ArrayMap<K, V>[]) new ArrayMap[DEFAULT_SIZE];
        this.clear();
        keyset = new HashSet<>();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private int hash(K key, int m) {
        if (key == null) {
            return 0;
        }
        int numBuckets = m;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("key cannot be null");
//        System.out.println(key.hashCode());
//        System.out.println(hash(key));
        return buckets[hash(key)].get(key);
        // if MyHashMap does not contain the key, then get() will return null;
    }

    /* Associates the specified value with the specified key in this map. */
    // cornercase not accounted for: if value is null, should call delete();
    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("key cannot be null");
        if (!containsKey(key)) {
            size += 1;
        }
        buckets[hash(key)].put(key, value);
        keyset.add(key);

        if (size*1.0/buckets.length >= MAX_LF) {
            resize(buckets.length * 2);
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    private void resize(int m) {
        ArrayMap<K, V>[] newbuckets = new ArrayMap[m];
        // FF: it is crucial to initialize each newbucket individually; or nullpointerexception.
        for (int i = 0; i < m; i++) {
            newbuckets[i] = new ArrayMap<>();
        }

        // FF: cannot just System.arraycopy(buckets, 0, newb, 0, size);
        // Need to recomputate the hash index for each element!
        // Alternatively, MyHashMap<K,V> temp = new MyHashMap<>(m) but this will require the writing of a new constructor
        for (int i = 0; i < buckets.length; i++) {
            for (K key : buckets[i].keySet()) {
                int index = hash(key, m);
                V val = buckets[i].get(key);
                ArrayMap<K,V> temp = newbuckets[index];
                temp.put(key,val);
//                newbuckets[hash(key,m)].put(key, buckets[i].get(key));
            }
        }
        buckets = newbuckets;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */

    @Override
    public Set<K> keySet() {
//        HashSet<K> keyset = new HashSet<>();
        //add a field in the instance variable to track the keyset (caching)
//        for (int i = 0; i < buckets.length; i++) {
//            for (K key : buckets[i].keySet()) {
//                keyset.add(key);
//            }
//            keyset.addAll(buckets[i].keySet());
//        }
//        return keyset;
        return keyset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (!this.containsKey(key)) {
            return null;
        }
        size -= 1;
        keyset.remove(key);
        V val = get(key);
        buckets[hash(key)].remove(key);
        return val;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (get(key) == value) {
            return remove(key);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("hello", 5);
        hashmap.put("cat", 10);
        hashmap.put("fish", 22);
        hashmap.put("zebra", 90);
        hashmap.put("hippo", 30);
        hashmap.put("giraffe", 28);
        hashmap.put("turtle", 60);
        hashmap.put("cat", 11);
        System.out.println(hashmap.size());
        System.out.println(hashmap.get("cat"));
        System.out.println(hashmap.get("dog"));
        System.out.println(hashmap.keySet());
        hashmap.remove("turtle");
        for (String item : hashmap) {
            System.out.println(hashmap.get(item));
        }

    }
}
