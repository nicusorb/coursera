import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by nicubucalaete on 22/04/16.
 */
@RunWith(Parameterized.class)
public class BoggleSolverTest {
    private final String dictionary;
    private final String board;
    private final int nbOfWords;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"dictionary-algs4.txt", "board4x4.txt", 29},
                {"dictionary-yawl.txt", "board4x4.txt", 204},
                {"dictionary-yawl.txt", "board-points2.txt", 2}
        });
    }

    public BoggleSolverTest(String dictionary, String board, int nbOfWords) {
        this.dictionary = dictionary;
        this.board = board;
        this.nbOfWords = nbOfWords;
    }

    @Test
    public void boogleTest() throws Exception {
        In in = new In(new File(getClass().getClassLoader().getResource(dictionary).toURI()));

        BoggleSolver solver = new BoggleSolver(in.readAllLines());

        Iterable<String> words = solver.getAllValidWords(new BoggleBoard(getClass().getClassLoader().getResource(board).toString()));

        int count = 0;
        for (String word : words) {
            count++;
//            System.out.println(word);
        }
        System.out.println(count);

        assertThat(count, is(nbOfWords));
    }
}