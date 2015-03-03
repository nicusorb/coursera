import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int[][] blocks;
    private final int hammingDistance;
    private final int manhattanDistance;
    private Board twin;

    // construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = clone(blocks);
        this.hammingDistance = computeHammingDistance();
        this.manhattanDistance = computeManhattanDistance();
    }

    private Board(int[][] blocks, boolean clone) {
        this.blocks = blocks;
        this.hammingDistance = computeHammingDistance();
        this.manhattanDistance = computeManhattanDistance();
    }

    // board dimension
    public int dimension() {
        return blocks.length;
    }

    // number of blocks out of place
    public int hamming() {
        return hammingDistance;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanDistance;
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
        if (twin == null)
            twin = buildTwinBoard();
        return twin;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();

        int row = 0, column = 0;
        for (int i = 0; i < blocks.length; i++) {
            column = findEmptyColumnLocation(i);
            if (column != -1) {
                row = i;
                break;
            }
        }

        if (row > 0) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row - 1][column];
            clonedBlocks[row - 1][column] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }
        if (row < dimension() - 1) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row + 1][column];
            clonedBlocks[row + 1][column] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }

        if (column > 0) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row][column - 1];
            clonedBlocks[row][column - 1] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }
        if (column < dimension() - 1) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row][column + 1];
            clonedBlocks[row][column + 1] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }
        return neighbors;
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

    private int[][] clone(int[][] blocksToClone) {
        int[][] clonedBlocks = new int[blocksToClone.length][blocksToClone.length];
        for (int i = 0; i < blocksToClone.length; i++) {
            for (int j = 0; j < blocksToClone[i].length; j++) {
                clonedBlocks[i][j] = blocksToClone[i][j];
            }
        }

        return clonedBlocks;
    }

    private int computeHammingDistance() {
        int hamming = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (!isInRightPlace(i, j)) {
                    hamming += 1;
                }
            }
        }

        return hamming;
    }

    private int computeManhattanDistance() {
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

        return manhattan;
    }

    private boolean isInRightPlace(int i, int j) {
        return blocks[i][j] == 0 || blocks[i][j] == (dimension() * i + j + 1);
    }

    private Board buildTwinBoard() {
        int[][] twinBoardBlocks = clone(blocks);
        int row, columnToExchange, column;

        do {
            row = new Random().nextInt(dimension());
            column = new Random().nextInt(dimension());

            if (column > 0) {
                columnToExchange = column - 1;
            } else {
                columnToExchange = column + 1;
            }
        } while (emptyBlockFound(twinBoardBlocks, row, columnToExchange, column));

        int tmp = twinBoardBlocks[row][columnToExchange];
        twinBoardBlocks[row][columnToExchange] = twinBoardBlocks[row][column];
        twinBoardBlocks[row][column] = tmp;

        return new Board(twinBoardBlocks, true);
    }

    private boolean emptyBlockFound(int[][] blocks, int row, int columnToExchange, int column) {
        return blocks[row][columnToExchange] == 0 || blocks[row][column] == 0;
    }

    private List<Board> buildNeighbors() {
        List<Board> neighbors = new LinkedList<>();

        int row = 0, column = 0;
        for (int i = 0; i < blocks.length; i++) {
            column = findEmptyColumnLocation(i);
            if (column != -1) {
                row = i;
                break;
            }
        }

        if (row > 0) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row - 1][column];
            clonedBlocks[row - 1][column] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }
        if (row < dimension() - 1) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row + 1][column];
            clonedBlocks[row + 1][column] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }

        if (column > 0) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row][column - 1];
            clonedBlocks[row][column - 1] = 0;
            neighbors.add(new Board(clonedBlocks, true));
        }
        if (column < dimension() - 1) {
            int[][] clonedBlocks = clone(blocks);
            clonedBlocks[row][column] = clonedBlocks[row][column + 1];
            clonedBlocks[row][column + 1] = 0;
            neighbors.add(new Board(clonedBlocks, true));
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
}
