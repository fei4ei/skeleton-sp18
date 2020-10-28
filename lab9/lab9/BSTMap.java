package lab9;

import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>, Iterable<K>{

    class Node {
        /* (K, V) pair stored in this Node. */
        K key;
        V value;
        private int size; //number of nodes in subtree

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v, int s) {
            key = k;
            value = v;
            size = s;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        // corner case
        if (key == null) {
            throw new IllegalArgumentException("call get() with a null key");
        }
        // base case
        if (p == null) {
            return null;
        }
        // K extends Comparable<K>, and therefore has the compareTo() method
        int cp = key.compareTo(p.key);
        if (cp < 0) {
            return getHelper(key, p.left);
        } else if (cp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        // if there is already the same key in the tree, the value associated with the key will be updated
        // base case: p is an empty null
        if (p == null) {
            return new Node(key, value, 1);
        }
        int cp = key.compareTo(p.key);
        if (cp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        p.size = 1 + size(p.left) + size(p.right);
        // p.left could be null so we cannot use p.left.size here
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Calls put() with a null key");
        }
        // this implementation did account for the corner case of value == null
        root = putHelper(key, value, root);
    }

    private int size(Node p) {
        if (p == null) {
            return 0;
        } else {
            return p.size;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /** Visitor pattern
     *
     * @param T
     * @param WhatToDo
     */
    void inorderTraverse(Node T, Action WhatToDo) {
        if (T == null) {
            return;
        }
        inorderTraverse(T.left, WhatToDo);
        WhatToDo.visit(T);
        inorderTraverse(T.right, WhatToDo);
    }

    class KeyToSet implements Action {
        TreeSet myKeySet = new TreeSet<>();

        @Override
        public void visit(BSTMap.Node T) {
            myKeySet.add(T.key);
        }
    }

    class KeyToStack implements Action {
        Stack myKeyStack = new Stack<>();

        @Override
        public void visit(BSTMap.Node T) {
            myKeyStack.push(T.key);
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        KeyToSet as = new KeyToSet();
        inorderTraverse(root, as);
        return as.myKeySet;
    }

    public Stack<K> keyStack() {
        KeyToStack ks = new KeyToStack();
        inorderTraverse(root, ks);
        return ks.myKeyStack;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        Stack ss = keyStack();
        return ss.iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        // System.out.println(bstmap.keySet());
        Iterator see = bstmap.iterator();
        while (see.hasNext()) {
            System.out.println(see.next());
        }

    }
}
