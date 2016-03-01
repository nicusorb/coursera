import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {
    private boolean solvable = true;
    private Stack<Board> solution = new Stack<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        pq.insert(new SearchNode(initial, 0, null));
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode searchNode;
        SearchNode searchNodeTwin;
        do {
            searchNode = getSearchNode(pq);
            searchNodeTwin = getSearchNode(twinPQ);
        } while (!searchNode.board.isGoal() && !searchNodeTwin.board.isGoal());

        if (searchNodeTwin.board.isGoal()) {
            solvable = false;
        } else {
            makeSolution(searchNode);
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
        if (solution.size() == 0)
            return null;
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        System.out.println("Running from file: " + args[0]);
        In in = new In(args[0]);
        int N = in.readInt();
        Stopwatch stopwatch = new Stopwatch();
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
            StdOut.println("Minimum number of moves = " + solver.moves() + " in: " + stopwatch.elapsedTime());
        }
    }

    private void makeSolution(SearchNode searchNode) {
        SearchNode it = searchNode;
        while (it != null) {
            solution.push(it.board);
            it = it.previous;
        }
    }

    private SearchNode getSearchNode(MinPQ<SearchNode> pq) {
        SearchNode searchNode = pq.delMin();

        for (Board board : searchNode.board.neighbors()) {
            if (!boardAlreadyAdded(searchNode, board)) {
                pq.insert(new SearchNode(board, searchNode.moves + 1, searchNode));
            }
        }
        return searchNode;
    }

    private boolean boardAlreadyAdded(SearchNode searchNode, Board board) {
        while (searchNode.previous != null) {
            if (board.equals(searchNode.previous.board))
                return true;
            searchNode = searchNode.previous;
        }
        return false;
//        return searchNode.previous == null || !board.equals(searchNode.previous.board);
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = manhattanPriority();
//            this.priority = hammingPriority();
        }

        private int hammingPriority() {
            return board.hamming() + moves;
        }

        private int manhattanPriority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode searchNode) {
            return priority - searchNode.priority;
        }

        @Override
        public String toString() {
            return board.toString() + " priority = " + priority;
        }
    }
}