package lab9;

import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;
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

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (size == 0) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return remove(root, key).value;
    }

    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (get(key) == value) {
            return remove(key);
        } else {
            throw new NoSuchElementException("did not find key-value pair");
        }
    }

    // page 411 of Sedgewick Algorithm
    private Node remove(Node x, K key) {
        if (x == null) { // base case #1
            return null;
        }
        int cp = key.compareTo(x.key);
        if (cp < 0) {
            x.left = remove(x.left, key);
        } else if (cp > 0) {
            x.right = remove(x.right, key);
        } else { // found the node to be removed
            if (x.right == null) { // first case: x has only left child
                return x.left; // x.left.size does not need to be updated
            }
            if (x.left == null) { // second case: x has only right child
                return x.right;
            }
            // third case: x has both left and right children
            Node t = x; // save a link to the node to be deleted in t
            x = min(t.right); // set x to point to its successor min(t.right)
            x.right = deleteMin(t.right); // set the right link of x to deleteMin(t.right)
            x.left = t.left; // set the left link of x (which was not null) to t.left
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public K min() {
        if (size == 0) {
            throw new NoSuchElementException("calls min() with empty map");
        }
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) { // base caser
            return x;
        } else { // recursion
            return min(x.left);
        }
    }


    @Override
    public Iterator<K> iterator() {
        Stack ss = keyStack();
        return ss.iterator(); // use the iterator of the stack class
        // alternatively, return keySet().iterator();
    }

    // Future projects: implement BST iterator by tracking the next node to be visited.
    // See Hilfinger's Data Structure into Java pp104

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("hippo", 30);
        bstmap.put("giraffe", 28);
        bstmap.put("turtle", 60);
        // System.out.println(bstmap.keySet());
//        Iterator see = bstmap.iterator();
//        while (see.hasNext()) {
//            System.out.println(see.next());
//        }
        for (String ss : bstmap) {
            System.out.println(ss);
        }
        System.out.println(bstmap.size());
        bstmap.remove("zebra");
        System.out.println(bstmap.size());

    }
}
