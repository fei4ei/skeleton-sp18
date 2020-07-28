import org.junit.Test;
import static org.junit.Assert.*;

public class testFlik {
    @Test
    public void testIsSameNum() {
        int a = 128;
        int b = 128;
        Integer c = 128;
        Integer d = java.lang.Integer.valueOf(a);
        Integer e = 128;
        assertEquals(a, b);
        assertEquals(c, d);
        assertTrue(c.equals(d));
        assertFalse(c==d);
        assertTrue(c.equals(e));
        assertFalse(c==e);
        assertTrue(Flik.isSameNumber(c, d));
        int i = 0;
        for (int j = 0; i < 500 ; i++, j++) {
            assertTrue(Flik.isSameNumber(i, j));
        }
    }
}