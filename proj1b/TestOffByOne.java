import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChar() {
        char a = 'a';
        char b = 'b';
        char c = 'c';
        char s1 = '%';
        char s2 = '&';
        char a1 = 'a';
        assertFalse(offByOne.equalChars(a,c));
        assertTrue(offByOne.equalChars(a,b));
        assertTrue(offByOne.equalChars(s1, s2));
        assertFalse(offByOne.equalChars(a, a1));
    }
}
