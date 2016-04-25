import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    private DeluxeTrieSET dictionary = new DeluxeTrieSET();
    private Set<String> boggleWords = new HashSet<>();
    private boolean[][] marked;
    private BoggleBoard board;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
//        Stopwatch stopwatch = new Stopwatch();
        for (String s : dictionary) {
            this.dictionary.add(s);
        }
//        System.out.println("Elapsed time to create the dictionary: " + stopwatch.elapsedTime());
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boggleWords.clear();
        this.board = board;
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                marked = new boolean[board.rows()][board.cols()];
                dfs(i, j, "");
            }
        }
        return boggleWords;
    }

    private void dfs(int i, int j, String prefix) {
        if (i < 0 || i >= board.rows() || j < 0 || j >= board.cols())
            return;
        if (!marked[i][j]) {
            String word = buildWord(i, j, prefix);
            if (!dictionary.hasKeyWithPrefix(word)) {
                return;
            }
            if (isValidWord(word))
                boggleWords.add(word);
            marked[i][j] = true;
            dfs(i - 1, j, word);
            dfs(i + 1, j, word);
            dfs(i, j - 1, word);
            dfs(i, j + 1, word);
            dfs(i - 1, j - 1, word);
            dfs(i + 1, j - 1, word);
            dfs(i - 1, j + 1, word);
            dfs(i + 1, j + 1, word);
            marked[i][j] = false;
        }
    }

    private String buildWord(int i, int j, String prefix) {
        char letter = board.getLetter(i, j);
        String word = prefix + letter;
        if (letter == 'Q')
            word += "U";
        return word;
    }

    private boolean isValidWord(String word) {
        return word.length() >= 3 && dictionary.contains(word);
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictionary.contains(word)) {
            final int length = word.length();
            if (length == 3 || length == 4) {
                return 1;
            } else if (length == 5) {
                return 2;
            } else if (length == 6) {
                return 3;
            } else if (length == 7) {
                return 5;
            } else if (length >= 8) {
                return 11;
            }
        }
        return 0;
    }
}