public class OffByOne implements CharacterComparator {


    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (Math.abs(diff) == 1);
        // char: primitive type; use x==y to compare
        // Character: wrapper reference type. use x.equals(y) to compare
    }
}
