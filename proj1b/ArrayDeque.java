public class ArrayDeque<Glorp> implements Deque<Glorp> {
    private int size;
    private int starter;
    private int RFACTOR = 2;
    private double UsageRatio = 0.25;
    private AList myList;

    // nested class AList as the inner layer of the circular array

    private class AList {
        public Glorp[] items;

        public AList(Glorp x) {
            items = (Glorp[]) new Object[8];
            items[0] = x;
        }

        //** creates an empty list.*/
        public AList() {
            items = (Glorp[]) new Object[8];
        }

        public int length() {
            return items.length;
        }
    }

    // constructor for myList as the outer layer of the circular array
    public ArrayDeque(Glorp x) {
            myList = new AList(x);
            starter = 0;
            size = 1;
    }
    public ArrayDeque() {
            myList = new AList();
            starter = 0;
            size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    private static int mod(int x, int y) {
        if (x >= 0) {
            return x%y;
        }
        else {
            return y+x%y;
        }
    }

    @Override
    public void addLast(Glorp x) {
        if (size == myList.length()) {
            resize(myList.length() * RFACTOR);
        }
        myList.items[mod(starter+size, myList.length())] = x;
        size += 1;
    }

    public Glorp getLast() {
        return myList.items[mod(starter+size-1, myList.length())];
    }

    @Override
    public Glorp removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        Glorp x = this.getLast();
        myList.items[mod(starter+size-1, myList.length())] = null;
        size -= 1;
        if ((size*1.0)/myList.length() < UsageRatio) {
            resize(Math.max(myList.length()/RFACTOR, 4));
            // if size is reduced to zero, and increased to non-zero number
        }
        return x;
    }

    @Override
    public void addFirst(Glorp x) {
        if (size == myList.length()) {
            resize(myList.length() * RFACTOR);
        }
        myList.items[mod((starter-1), myList.length())] = x;
        size += 1;
        starter = mod((starter-1), myList.length());
    }

    public Glorp getFirst() {
        return myList.items[mod(starter, myList.length())];
    }

    @Override
    public Glorp removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        Glorp x = myList.items[starter];
        myList.items[starter] = null;
        starter = mod((starter+1), myList.length());
        size -= 1;
        if ((size*1.0)/myList.length() < UsageRatio) {
            resize(Math.max(myList.length()/RFACTOR, 4));
        }
        return x;
    }

    @Override
    public Glorp get(int i) {
        return myList.items[mod(starter+i, myList.length())];
    }

    @Override
    public void printDeque() {
        for (int i=starter; i<starter+size; i++) {
            System.out.println(myList.items[mod(i, myList.length())]);
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    private void resize(int capacity) {
        Glorp[] a = (Glorp[]) new Object[capacity];
        // System.arraycopy(myList.items, starter, a, 0, size);
        for (int i = 0; i < size; i++) {
                a[i] = myList.items[mod(starter+i, myList.length())];
        }
        myList.items = a;
        starter = 0;
    }

    private int length() {
        return myList.items.length; // return myList.length();
    }

    public static void main(String[] args) {
        // System.out.println(mod(-1,10) + " " + mod(-2,10) + " " + mod(-13,10));
        // System.out.println(Math.max(0,3));

        ArrayDeque<Integer> intL = new ArrayDeque<>();
        System.out.println(intL.getLast());
        System.out.println(intL.removeLast());
        System.out.println(intL.removeLast());
        System.out.println(intL.removeLast());
        System.out.println(intL.removeLast());
        System.out.println(intL.getFirst());
        intL.printDeque();

        ArrayDeque<String> stringL = new ArrayDeque<>("This");
        System.out.println(stringL.size());
        System.out.println(stringL.length());
        // System.out.println(stringL.getFirst());
        // System.out.println(stringL.size());
        stringL.addLast("is");
        stringL.addFirst("Hello! ");
        stringL.addFirst("Are you there?");
        stringL.addFirst("works");
        stringL.addFirst("resize");
        stringL.addFirst("if");
        stringL.addFirst("see");
        stringL.addFirst("us");
        stringL.addFirst("Let");
        stringL.addLast("FF!");
        stringL.printDeque();

        stringL.removeFirst();
        stringL.removeFirst();
        stringL.removeFirst();
        stringL.removeFirst();
        stringL.removeFirst();
        stringL.removeFirst();
        stringL.removeFirst();
        stringL.removeLast();
        stringL.removeLast();
        stringL.removeLast();
        stringL.printDeque();
        stringL.removeLast();
        System.out.println(stringL.size());
        System.out.println(stringL.length());
        stringL.addFirst("Back");
        stringL.addLast("to");
        stringL.addLast("business");
        stringL.printDeque();


        // intL.addFirst(10);
        // intL.printDeque();

        /*
        System.out.println(stringL.get(2));
        System.out.println(stringL.removeLast());
        System.out.println(stringL.removeFirst());
        System.out.println(stringL.getLast());
        System.out.println(stringL.getFirst());
        stringL.resize(16);
        System.out.println(stringL.length());
        stringL.printDeque();

        */
    }
}