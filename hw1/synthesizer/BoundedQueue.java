package synthesizer;

public interface BoundedQueue<T> {
    //return size of the buffer
    int capacity();

    // return number of items currently in the buffer
    int fillCount();

    //add item x to the end
    void enqueue(T x);

    //delete and return item from the front
    T dequeue();

    //return (but do not delete) item from the front
    T peek();

    // Is the buffer empty?
    default boolean isEmpty() {
        return (fillCount() == 0);
    }

    default boolean isFull() {
        return (fillCount() == capacity());
    }

}
