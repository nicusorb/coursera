import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SolverTest extends AbstractTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{"puzzle04.txt", "puzzle04Output.txt"}});
    }

    public SolverTest(String inputFileToBeChecked, String correctOutput) {
        super(inputFileToBeChecked, correctOutput);
    }

    @Override
    public void generateOutput() {
        Solver.main(new String[]{inputFileToBeChecked});
    }

    @Test
    public void isSolvable_test() throws Exception {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        Board board = new Board(blocks);

        assertThat(new Solver(board).isSolvable(), is(false));
    }

    @Test
    public void solution_shouldReturnNullIfNoSolutionIsFound() throws Exception {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        Board board = new Board(blocks);

        assertNull(new Solver(board).solution());
    }

    @Test
    public void testName() throws Exception {
        int[][] blocks = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
        Board board = new Board(blocks);

        assertThat(new Solver(board).isSolvable(), is(true));

    }
}
