import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @ Test
    public void testisPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("c"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("isaasi"));
        assertFalse(palindrome.isPalindrome("isabsi"));
        assertTrue(palindrome.isPalindrome("isasi"));
        assertFalse(palindrome.isPalindrome("isaasm"));
    }

    @Test
    public void testisPalindromeR() {
        assertFalse(palindrome.isPalindromeR("cat"));
        assertTrue(palindrome.isPalindromeR("c"));
        assertTrue(palindrome.isPalindromeR(""));
        assertTrue(palindrome.isPalindromeR("deed"));
        assertFalse(palindrome.isPalindromeR("does"));
        assertTrue(palindrome.isPalindromeR("tenet"));
        assertFalse(palindrome.isPalindromeR("tenis"));
    }

    @Test
    public void testIsPalindrome() {
        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertTrue(palindrome.isPalindrome("flke", offByOne));
        assertTrue(palindrome.isPalindrome("c",offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertFalse(palindrome.isPalindrome("issac", offByOne));
        assertFalse(palindrome.isPalindrome("isac", offByOne));

    }

    @Test
    public void testIsPalindromeN() {
        CharacterComparator offBy3 = new OffByN(3);
        assertTrue(palindrome.isPalindrome("bore", offBy3));
        assertTrue(palindrome.isPalindrome("bogle", offBy3));
        assertFalse(palindrome.isPalindrome("flake", offBy3));
        assertTrue(palindrome.isPalindrome("m", offBy3));
    }

}
