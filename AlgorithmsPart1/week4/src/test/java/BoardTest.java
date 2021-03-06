import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void hamming_test() throws Exception {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(board.hamming(), is(5));
    }

    @Test
    public void hamming_for2X2_matrix() throws Exception {
        int[][] blocks = {{1, 3}, {2, 0}};
        Board board = new Board(blocks);

        assertThat(board.hamming(), is(2));
    }

    @Test
    public void manhattan_test() throws Exception {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);

        assertThat(board.manhattan(), is(10));

        int[][] blocks2 = {{0, 2}, {3, 1}};
        board = new Board(blocks2);

        assertThat(board.manhattan(), is(2));
    }

    @Test
    public void isGoal_returnsTrue_ifItIsTheFinalBoard() throws Exception {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(board.isGoal(), is(true));
    }

    @Test
    public void isGoal_returnsFalse_ifItIsNotTheFinalBoard() throws Exception {
        int[][] blocks = {{1, 2, 3}, {4, 6, 5}, {7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(board.isGoal(), is(false));
    }

    @Test
    public void toString_test() throws Exception {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);

        assertThat(board.toString(), is("3\n1 2 3\n4 5 6\n7 8 0\n"));
    }

    @Test
    public void twin_test() throws Exception {
        int[][] blocks = {{0, 1}, {2, 3}};
        Board board = new Board(blocks);

        Board twinBoard = board.twin();

//        assertThat(twinBoard.getBlock(1, 0), is(3));
//        assertThat(twinBoard.getBlock(1, 1), is(2));
    }

    @Test
    public void twinBoard_isTheSameOnConsecutiveCalls() throws Exception {
        int[][] blocks = {{0, 1}, {2, 3}};
        Board board = new Board(blocks);

        Board twinBoard1 = board.twin();
        Board twinBoard2 = board.twin();

        assertEquals(twinBoard1, twinBoard2);
    }
}
