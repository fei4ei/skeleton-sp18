public class OffByN implements CharacterComparator {
    public int Number;

    public OffByN(int N) {
        Number = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        return (Math.abs(diff) == Number);

    }
}
