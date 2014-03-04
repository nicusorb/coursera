
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
        out += moves + "\n";
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
        return null;
    }
//    public boolean equals(Object y)        // does this board equal y?

    private boolean isInRightPlace(int i, int j) {
        return blocks[i][j] == 0 || blocks[i][j] == (dimension() * i + j + 1);
    }
}
