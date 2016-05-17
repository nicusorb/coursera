public class CircularSuffixArray {
    private final int length;
    private int[] indexes;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        this.length = s.length();
        this.indexes = new int[length()];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        Quick3string.sort(indexes, s);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        return indexes[i];
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }
}
