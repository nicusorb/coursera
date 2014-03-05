import java.util.LinkedList;
import java.util.List;

public class Board {
    private Board previous;
    private int[][] blocks;
    private int moves;

    // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this(null, blocks, 0);
    }

    public Board(Board previous, int[][] blocks, int moves) {
        this.previous = previous;
        this.blocks = blocks;
        this.moves = moves;
    }

    private Board(Board b) {
        this.previous = null;
        this.moves = b.moves + 1;
        this.blocks = new int[b.dimension()][b.dimension()];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.blocks[i][j] = b.blocks[i][j];
            }
        }
    }

    // board dimension dimension
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (!isInRightPlace(i, j)) {
                    hamming += 1;
                }
            }
        }

        return hamming + moves;
    }

    // sum of Manhattan distances between blocks and goal
//    public int manhattan() {
//        //TODO
//        int manhattan = 0;
//
//        return manhattan + moves;
//    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (!isInRightPlace(i, j))
                    return false;
            }
        }
        return true;
    }

    //    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row

    // string representation of the board (in the output format specified below)
    public String toString() {
        String out = "";
        out += dimension() + "\n";
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (j < blocks[i].length - 1) {
                    out += blocks[i][j] + " ";
                } else {
                    out += blocks[i][j];
                }
            }
            out += "\n";
        }

        return out;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<Board>();

        int row = 0, column = 0;
        for (int i = 0; i < blocks.length; i++) {
            column = findEmptyColumnLocation(i);
            if (column != -1) {
                row = i;
                break;
            }
        }

        if (row > 0) {
            Board board = new Board(this);
            board.blocks[row][column] = board.blocks[row - 1][column];
            board.blocks[row - 1][column] = 0;
            neighbors.add(board);
        }
        if (row < dimension() - 1) {
            Board board = new Board(this);
            board.blocks[row][column] = board.blocks[row + 1][column];
            board.blocks[row + 1][column] = 0;
            neighbors.add(board);
        }

        if (column > 0) {
            Board board = new Board(this);
            board.blocks[row][column] = board.blocks[row][column - 1];
            board.blocks[row][column - 1] = 0;
            neighbors.add(board);
        }
        if (column < dimension() - 1) {
            Board board = new Board(this);
            board.blocks[row][column] = board.blocks[row][column + 1];
            board.blocks[row][column + 1] = 0;
            neighbors.add(board);
        }
        return neighbors;
    }

    private int findEmptyColumnLocation(int row) {
        for (int j = 0; j < blocks[row].length; j++) {
            if (blocks[row][j] == 0) {
                return j;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Board board = (Board) o;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = previous != null ? previous.hashCode() : 0;
        result = 31 * result + moves;
        return result;
    }

    private boolean isInRightPlace(int i, int j) {
        return blocks[i][j] == 0 || blocks[i][j] == (dimension() * i + j + 1);
    }
}
