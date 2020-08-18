import java.util.ArrayList;

public class Palindrome {
    /** Given a String, wordToDeque should return a Deque
     * where the characters appear in the same order as in the String.
     */

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> temp = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            temp.addLast(word.charAt(i));

        }
        return temp;
    }

    /** The isPalindrome method should return true if the given word is a palindrome,
     * and false otherwise. A palindrome is defined as a word that is the same whether
     * it is read forwards or backwards. This method uses iteration.
     * @param word
     * @return
     */
    public boolean isPalindrome(String word) {
        Deque<Character> myDeque = wordToDeque(word);
        // if word.length = 1 or 0, (int) word.length()/2.0 = 0
        // if word.length = 2, (int) word.length()/2.0 = 1
        for (int i = 1; i <= (int) word.length()/2.0; i++) {
            Character front = myDeque.removeFirst();
            Character back = myDeque.removeLast();
            if (front != back) {
                return false;
            }
        }
        return true;
    }

    /** The private isPalindrome helper method should return true if the given word is a palindrome,
     * and false otherwise. A palindrome is defined as a word that is the same whether
     * it is read forwards or backwards. This method uses recursion.
     * @param myDeque
     * @return
     */

    private boolean isPalindromeRP(Deque<Character> myDeque) {
        // base case
        if ((myDeque.size() == 1) || (myDeque.size() == 0)) {
            return true;
        }

        // Setting up the recursion
        Character front = myDeque.removeFirst();
        Character back = myDeque.removeLast();
        if (front != back) {
            return false;
        } else {
            return isPalindromeRP(myDeque);
        }
    }

    public boolean isPalindromeR(String word) {
        Deque<Character> myDeque = wordToDeque(word);
        return isPalindromeRP(myDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        for (int i = 0; i <= (int) word.length()/2.0-1; i++) {
            char front = word.charAt(i);
            char back = word.charAt(word.length()-1-i);
            if (!cc.equalChars(front, back)) {
                return false;
            }
        }
            return true;
    }
}
