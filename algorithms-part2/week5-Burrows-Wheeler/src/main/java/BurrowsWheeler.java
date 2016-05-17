import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        String input = BinaryStdIn.readString();
        char[] transform = new char[input.length()];
        int transformIndex = -1;

        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(input);


        for (int i = 0; i < circularSuffixArray.length(); i++) {
            int indexInOriginalSuffixesArray = circularSuffixArray.index(i);
            if (indexInOriginalSuffixesArray == 0) {
                transformIndex = i;
                indexInOriginalSuffixesArray = input.length();
            }
            transform[i] = input.charAt(indexInOriginalSuffixesArray - 1);
        }

        writeTransformation(transform, transformIndex);
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        char[] charsToDecode = s.toCharArray();

        int[] next = computeNextArray(charsToDecode);
        decodeStringAndWriteToOutput(first, charsToDecode, next);
        BinaryStdOut.close();
    }

    private static void writeTransformation(char[] transform, int transformIndex) {
        BinaryStdOut.write(transformIndex);

        for (int i = 0; i < transform.length; i++) {
            BinaryStdOut.write(transform[i]);
        }

        BinaryStdOut.close();
    }

    private static int[] computeNextArray(char[] charsToDecode) {
        int[] next = new int[charsToDecode.length];
        char lastOrderedChar = (char) -1;
        int searchStartPosition = 0;

        char[] orderedChars = getOrderedChars(charsToDecode);

        for (int i = 0; i < orderedChars.length; i++) {
            char orderedChar = orderedChars[i];
            if (orderedChar != lastOrderedChar) {
                searchStartPosition = 0;
            }

            for (; searchStartPosition < next.length; searchStartPosition++) {
                if (charsToDecode[searchStartPosition] == orderedChar) {
                    next[i] = searchStartPosition;
                    searchStartPosition++;
                    break;
                }
            }

            lastOrderedChar = orderedChar;
        }

        return next;
    }

    private static char[] getOrderedChars(char[] charsToDecode) {
        char[] orderedChars = charsToDecode.clone();
        KeyIndexSort.sort(orderedChars);
        return orderedChars;
    }

    private static void decodeStringAndWriteToOutput(int first, char[] charsToDecode, int[] next) {
        for (int nbOfCharWritten = 0; nbOfCharWritten < charsToDecode.length; nbOfCharWritten++) {
            BinaryStdOut.write(charsToDecode[next[first]]);
            first = next[first];
        }
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("-"))
            encode();
        else if (args[0].equalsIgnoreCase("+"))
            decode();
    }
}