import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Board {
    private int[][] blocks;
    private int moves;

    // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        this.moves = 0;
    }

    private Board(Board b) {
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
    public int manhattan() {
        int manhattan = 0;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0)
                    continue;
                int goalRow = (blocks[i][j] - 1) / dimension();
                int goalColumn = (blocks[i][j] - 1) % dimension();

                manhattan += Math.abs(goalRow - i) + Math.abs(goalColumn - j);
            }
        }

        return manhattan + moves;
    }

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

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board twinBoard = new Board(this);
        int row = -1, columnToExchange = -1, column = -1;

        do {
            row = new Random().nextInt(dimension());
            column = new Random().nextInt(dimension());

            if (column > 0) {
                columnToExchange = column - 1;
            } else if (column < dimension() - 1) {
                columnToExchange = column + 1;
            }
        } while (twinBoard.blocks[row][columnToExchange] == 0 ||
                twinBoard.blocks[row][column] == 0);

        int tmp = twinBoard.blocks[row][columnToExchange];
        twinBoard.blocks[row][columnToExchange] = twinBoard.blocks[row][column];
        twinBoard.blocks[row][column] = tmp;

        return twinBoard;
    }

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

        if (this.dimension() != board.dimension())
            return false;

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (blocks[i][j] != board.blocks[i][j])
                    return false;
            }
        }
        return true;
    }

    private boolean isInRightPlace(int i, int j) {
        return blocks[i][j] == 0 || blocks[i][j] == (dimension() * i + j + 1);
    }
}
