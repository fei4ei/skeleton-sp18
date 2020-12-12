package lab10;

import edu.princeton.cs.algs4.Stack;

public class dis10_2<T> {
    public class maxheap {
        ArrayHeap<T> minheap;
        Stack<T> maxstack;
        public maxheap(ArrayHeap myheap) {
            minheap = myheap;
            maxstack = new Stack<T>();
        }
        public void intoStack() {
            while (minheap.size() > 0) {
                maxstack.push(minheap.removeMin());
            }
        }

        public void outofStack() {
            while (maxstack.size() > 0) {
                T val = maxstack.pop();
                minheap.insert(val, val.hashCode());
            }
        }

        
    }
}
