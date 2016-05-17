import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int ALPHABET_SIZE = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] sequence = initSequence();

        while (!BinaryStdIn.isEmpty()) {
            char readedChar = BinaryStdIn.readChar();
            char pos = getPosition(readedChar, sequence);
            BinaryStdOut.write(pos);
            moveCharToFront(sequence, readedChar, pos);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] sequence = initSequence();

        while (!BinaryStdIn.isEmpty()) {
            char readedChar = BinaryStdIn.readChar();
            BinaryStdOut.write(sequence[readedChar]);
            moveCharToFront(sequence, sequence[readedChar], readedChar);
        }
        BinaryStdOut.close();
    }

    private static char[] initSequence() {
        char[] sequence = new char[ALPHABET_SIZE];
        for (char i = 0; i < sequence.length; i++) {
            sequence[i] = i;
        }
        return sequence;
    }

    private static char getPosition(char readedChar, char[] sequence) {
        char pos = 0;
        for (char i = 0; i < sequence.length; i++) {
            if (readedChar == sequence[i]) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    private static void moveCharToFront(char[] sequence, char readedChar, char pos) {
        for (char i = pos; i > 0; i--) {
            sequence[i] = sequence[i - 1];
        }
        sequence[0] = readedChar;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("-"))
            encode();
        else if (args[0].equalsIgnoreCase("+"))
            decode();
    }
}