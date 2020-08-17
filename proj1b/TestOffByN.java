import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy3 = new OffByN(3);

    @Test
    public void testEqualChar() {

        char a = 'a';
        char b = 'b';
        char c = 'c';
        char d = 'd';
        char e = 'e';
        char a1 = 'a';
        assertFalse(offBy3.equalChars(a,b));
        assertTrue(offBy3.equalChars(a,d));
        assertTrue(offBy3.equalChars(e,b));
        assertFalse(offBy3.equalChars(a,a1));
    }

    @Test
    public void testOffBy3() {

    }

}
