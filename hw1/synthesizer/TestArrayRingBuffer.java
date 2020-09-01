package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(5);
        arb.enqueue(10);
        arb.enqueue(15);


        Integer expected = 5;
        assertEquals(expected, arb.dequeue());
        assertEquals((Integer) 10, arb.dequeue());
        assertEquals((Integer) 15, arb.dequeue());

        for (Integer a : arb) {
            System.out.println(a);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
