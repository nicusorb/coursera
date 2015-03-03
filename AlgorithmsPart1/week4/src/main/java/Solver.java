import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solver {
    private boolean solvable = true;
    private List<Board> solution = new LinkedList<>();

//    find a solution to the initial board (using the A* algorithm)

    public Solver(Board initial) {
        List<Board> solutionsFound = new LinkedList<>();
        List<Board> solutionsFoundTwin = new LinkedList<>();
        List<Board> solutionTwin = new LinkedList<>();

        MinPQ<Board> minPQ = new MinPQ<>(10, new MinBoardComparator());
        MinPQ<Board> minPQTwin = new MinPQ<>(10, new MinBoardComparator());

        minPQ.insert(initial);
        minPQTwin.insert(initial.twin());

        solutionsFound.add(initial);
        solutionsFoundTwin.add(initial.twin());

        Board minBoard, minBoardTwin;
        do {
            minBoard = getBoard(minPQ, solution, solutionsFound);
            minBoardTwin = getBoard(minPQTwin, solutionTwin, solutionsFoundTwin);
        } while (!minBoard.isGoal() && !minBoardTwin.isGoal());

        solutionTwin.clear();
        if (minBoardTwin.isGoal()) {
            solvable = false;
            solution.clear();
        }
    }

    private Board getBoard(MinPQ<Board> pq, List<Board> solutions, List<Board> solutionsFound) {
        Board minBoard;
        minBoard = pq.delMin();
//            while (!pq.isEmpty())
//                pq.delMin();

        solutions.add(minBoard);
        for (Board board : minBoard.neighbors()) {
            if (!solutionsFound.contains(board)) {
                pq.insert(board);
                solutionsFound.add(board);
            }
        }
        return minBoard;
    }

    // find a solution to the initial board (using the A* algorithm)

//    public Solver(Board initial) {
//        MinPQ<SearchNode> pq = new MinPQ<>(10);
//        MinPQ<SearchNode> twinPQ = new MinPQ<>(10);
//
//        pq.insert(new SearchNode(initial, 0, null));
//        twinPQ.insert(new SearchNode(initial.twin(), 0, null));
//
//        solutionsFound.add(initial);
//        SearchNode searchNode, searchNodeTwin;
//        do {
//            searchNode = getSearchNode(pq, solution);
//            searchNodeTwin = getSearchNode(twinPQ, solutionTwin);
//        } while (!searchNode.board.isGoal() && !searchNodeTwin.board.isGoal());
//
//        solutionTwin.clear();
//        if (searchNodeTwin.board.isGoal()) {
//            solvable = false;
//            solution.clear();
//        }
//    }
//
//    private SearchNode getSearchNode(MinPQ<SearchNode> pq, List<Board> solution) {
//        SearchNode searchNode = pq.delMin();
//        empty(pq);
//        solution.add(searchNode.board);
//
//        for (Board board : searchNode.board.neighbors()) {
//            if (!board.equals(searchNode.previousBoard)) {
//                pq.insert(new SearchNode(board, searchNode.moves + 1, searchNode.board));
//            }
//        }
//        return searchNode;
//    }
//
//    private void empty(MinPQ<SearchNode> pq) {
//        while (!pq.isEmpty()) {
//            SearchNode node = pq.delMin();
//            node.previousBoard = null;
//            node.board = null;
//        }
//    }

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

//    private class SearchNode implements Comparable<SearchNode> {
//        private Board board;
//        private int moves;
//        private Board previousBoard;
//
//        public SearchNode(Board board, int moves, Board previousBoard) {
//            this.board = board;
//            this.moves = moves;
//            this.previousBoard = previousBoard;
//        }
//
//        @Override
//        public int compareTo(SearchNode searchNode) {
//            return this.board.manhattan() + this.moves - searchNode.board.manhattan() - searchNode.moves;
//        }
//    }

    private class MinBoardComparator implements Comparator<Board> {
        @Override
        public int compare(Board b1, Board b2) {
            int b1Priority = b1.manhattan();
            int b2Priority = b2.manhattan();

            return b1Priority - b2Priority;
        }
    }
}