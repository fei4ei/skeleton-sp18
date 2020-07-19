public class LinkedListDeque<BleepBlorp> {
    /** LinkedListDeque is a doubly linked list of generic types, which hides the nakedness within.
     */
    private IndNode sentinel;
    private int size;

    // nested class of the naked IndNode class of doubly linked list
    // IndNode cannot be static b/c non-static variable BleepBlorp is referenced here
    private class IndNode {
        public IndNode prev;
        public BleepBlorp item;
        public IndNode next;

        public IndNode(IndNode PrevPointer, BleepBlorp CurrentItem, IndNode NextPointer) {
            prev = PrevPointer;
            item = CurrentItem;
            next = NextPointer;
        }

        // let me read about public vs. private method in a private nested class
        // a helper method for getRecursive of LinkedListDeque.
        // cannot be static b/c we use the BleepBlorp generic type
        public BleepBlorp getR(int i) {
            IndNode aNode = this;
            if (i == 0) {
                return aNode.next.item;
            }
            return aNode.next.getR(i-1);
        }

    }

    // constructor for 1 element list
    public LinkedListDeque(BleepBlorp x){
        sentinel = new IndNode(null, null, null);
        sentinel.next = new IndNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    // constructor for an empty list
    public LinkedListDeque() {
        sentinel = new IndNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }

    public void addFirst(BleepBlorp x) {
        IndNode aNode = new IndNode(sentinel, x, sentinel.next);
        sentinel.next = aNode;
        aNode.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(BleepBlorp x) {
        IndNode aNode = new IndNode(sentinel.prev, x, sentinel);
        sentinel.prev = aNode;
        aNode.prev.next = aNode;
        size += 1;
    }

    public BleepBlorp removeFirst() {
        if (size > 0) {
            IndNode first = sentinel.next;
            sentinel.next = first.next;
            first.next.prev = sentinel;
            size -= 1;
            return first.item;
        }
        return null;
    }

    public BleepBlorp removeLast() {
        if (size > 0) {
            IndNode last = sentinel.prev;
            sentinel.prev = last.prev;
            last.prev.next = sentinel;
            size -= 1;
            return last.item;
        }
        return null;
    }

    // iterative method for accessing the ith element of the LinkedListDeque
    public BleepBlorp get(int i) {
        IndNode aNode = sentinel;
        for (int j = 0; j < i+1; j++) {
            aNode = aNode.next;
        }
        return aNode.item;
    }
    // recursive method for accessing the ith element of the LinkedListDeque with the help of a private getR method of IndNode
    public BleepBlorp getRecursive(int i) {
        return sentinel.getR(i);
    }

    public void printDeque() {
        IndNode aNode = sentinel;
        for (int i = 0; i < size; i++) {
            System.out.println(aNode.next.item);
            aNode = aNode.next;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<String> stringL = new LinkedListDeque<>("this ");
        System.out.println(stringL.isEmpty());
        stringL.addFirst("Hello, ");
        stringL.addLast("is");
        stringL.addLast("me");
        stringL.printDeque();
        System.out.println(stringL.get(0));
        System.out.println(stringL.get(1));
        System.out.println(stringL.get(2));
        System.out.println(stringL.get(3));
        System.out.println(stringL.getRecursive(3));
        LinkedListDeque<Integer> integerL = new LinkedListDeque<>();
        System.out.println(integerL.isEmpty());
        System.out.println(integerL.removeLast());
        integerL.addLast(9);
        System.out.println(integerL.removeLast());
    }

}