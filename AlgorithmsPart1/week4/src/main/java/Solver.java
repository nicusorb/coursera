import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solver {
    private boolean solvable = true;
    private List<Board> solution = new LinkedList<Board>();
    private List<Board> solution2 = new LinkedList<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Board> minPQ = new MinPQ<Board>(10, new MinBoardComparator());
        MinPQ<Board> minPQ2 = new MinPQ<Board>(10, new MinBoardComparator());

        minPQ.insert(initial);
        minPQ2.insert(initial.twin());

        Board minBoard, minBoard2;
        do {
            minBoard = minPQ.delMin();
            solution.add(minBoard);
            for (Board board : minBoard.neighbors()) {
                if (!solution.contains(board))
                    minPQ.insert(board);
            }

            minBoard2 = minPQ2.delMin();
            solution2.add(minBoard2);
            for (Board board : minBoard2.neighbors()) {
                if (!solution2.contains(board))
                    minPQ2.insert(board);
            }
        } while (!minBoard.isGoal() && !minBoard2.isGoal());

        solution2.clear();
        if (minBoard2.isGoal()) {
            solvable = false;
            solution.clear();
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return solution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return solution;
    }


    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class MinBoardComparator implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            int b1Priority = b1.manhattan();
            int b2Priority = b2.manhattan();

            if (b1Priority < b2Priority)
                return -1;
            else if (b1Priority == b2Priority)
                return 0;
            return 1;
        }
    }
}