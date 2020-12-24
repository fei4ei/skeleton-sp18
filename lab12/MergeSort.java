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
        Queue<Item> aux = new Queue<>();
        for (Item i : items) {
            aux.enqueue(i);
        }
        Queue<Queue<Item>> toReturn = new Queue<>();
        while (!aux.isEmpty()) {
            Queue<Item> temp = new Queue<>();
            temp.enqueue(aux.dequeue());
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
        // FF: create temp and temp1 so that the original queue will not be altered and we return a new queue
        Queue<Item> temp = new Queue<>();
        Queue<Item> temp1 = new Queue<>();
        Queue<Item> toReturn = new Queue<>();

        for (Item i : q1) {
            temp.enqueue(i);
        }
        for (Item i : q2) {
            temp1.enqueue(i);
        }
        while (!temp.isEmpty() || !temp1.isEmpty()) {
            Item min = getMin(temp, temp1);
            toReturn.enqueue(min);
        }
        return toReturn;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        int size = items.size();
        Queue<Item> aux = new Queue<>();
        for (Item i : items) {
            aux.enqueue(i);
        }
//        Queue<Queue<Item>> iQ = makeSingleItemQueues(items); // makeSingleItemQueues is a destructive method
        // base case
        if (size == 0) return new Queue<Item>();
        if (size == 1) return aux;

        Queue<Item> iQ1 = new Queue<>();
        Queue<Item> iQ2 = new Queue<>();
        // recursive algorithm to mergesort each half
        for (int i = 0; i < size/2; i++) {
            iQ1.enqueue(aux.dequeue());
        }
        while (!aux.isEmpty()) {
            iQ2.enqueue(aux.dequeue());
        }
        Queue<Item> iQ3 = mergeSort(iQ1); // Note that mergeSort sorts destructively, rather than sort in place
        Queue<Item> iQ4 = mergeSort(iQ2);
        return mergeSortedQueues(iQ3, iQ4);
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        Queue<String> students1 = new Queue<String>();
        students.enqueue("Alice");
//        students.enqueue("Alice");
//        students.enqueue("Alice");
        students.enqueue("Emily");
        students.enqueue("Vanessa");
        students.enqueue("Zoey");
        students.enqueue("Talia");
        students.enqueue("Maria");
        students.enqueue("Nina");
        students.enqueue("Kathy");
        students.enqueue("Susan");
        students.enqueue("Penelope");
//        System.out.println("students: " + students);
        System.out.println(students);
//        System.out.println(mergeSortedQueues(students, students1));
//        Queue<Queue<String>> temp = makeSingleItemQueues(students);
//        System.out.println(temp.toString());
        Queue<String> sortedStudents = mergeSort(students);
        System.out.println("after sorting: " + sortedStudents);
//        System.out.println("empty: " + mergeSort(students1));
    }
}
