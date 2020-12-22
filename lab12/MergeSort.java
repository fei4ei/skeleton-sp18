import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        Queue<Queue<Item>> toReturn = new Queue<>();
        while (!items.isEmpty()) {
            Queue<Item> temp = new Queue<>();
            temp.enqueue(items.dequeue());
            toReturn.enqueue(temp);
        }
        return toReturn;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> toReturn = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            Item min = getMin(q1, q2);
            toReturn.enqueue(min);
        }
        return toReturn;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        int size = items.size();
        Queue<Queue<Item>> iQ = makeSingleItemQueues(items);
        Queue<Item> iQ1 = new Queue<>();
        Queue<Item> iQ2 = new Queue<>();

        // base case
        if (size == 0) return null;
        if (size == 1) return items;

        // recursive algorithm to mergesort each half
        for (int i = 0; i < size/2; i++) {
            iQ1.enqueue(iQ.dequeue().dequeue());
            mergeSort(iQ1);
        }
        while (!iQ.isEmpty()) {
            iQ2.enqueue(iQ.dequeue().dequeue());
            mergeSort(iQ2);
        }
        mergeSortedQueues(iQ1, iQ2);
        return items;
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Emily");
        students.enqueue("Vanessa");
        students.enqueue("Zoey");
        students.enqueue("Talia");
        students.enqueue("Maria");
        students.enqueue("Nina");
        students.equals("Kathy");
        students.enqueue("Susan");
        students.enqueue("Jane");
        students.enqueue("Penelope");
        System.out.println(students);
        System.out.println(mergeSort(students));
    }
}
