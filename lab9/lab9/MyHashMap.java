package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    // do I need to resize ArrayMap.keys and values???
    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
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
            }
        }
        buckets = newbuckets;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> hashmap = new MyHashMap<>();
        hashmap.put("hello", 5);
        hashmap.put("cat", 10);
        hashmap.put("fish", 22);
        System.out.println(hashmap.size());
        System.out.println(hashmap.get("cat"));

    }
}
