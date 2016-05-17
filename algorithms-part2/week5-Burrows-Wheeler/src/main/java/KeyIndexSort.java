public class KeyIndexSort {
    /**
     * Rearranges the array of W-character strings in ascending order.
     *
     * @param a the array to be sorted
     * @see edu.princeton.cs.algs4.LSD#sort(String[], int)
     */
    public static void sort(char[] a) {
        int N = a.length;
        int R = 256;   // extend ASCII alphabet size
        char[] aux = new char[N];


        // compute frequency counts
        int[] count = new int[R + 1];
        for (int i = 0; i < N; i++)
            count[a[i] + 1]++;

        // compute cumulates
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];

        // move data
        for (int i = 0; i < N; i++)
            aux[count[a[i]]++] = a[i];

        // copy back
        for (int i = 0; i < N; i++)
            a[i] = aux[i];
    }
}
