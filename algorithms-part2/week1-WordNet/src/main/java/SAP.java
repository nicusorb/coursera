import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = new Digraph(G.V());
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                digraph.addEdge(v, w);
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        Ancestor ancestor = computeAncestor(v, w);
        return ancestor.dist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        Ancestor ancestor = computeAncestor(v, w);
        return ancestor.vertex;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        Ancestor ancestor = computeAncestor(v, w);
        return ancestor.dist;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        Ancestor ancestor = computeAncestor(v, w);
        return ancestor.vertex;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private Ancestor computeAncestor(int v, int w) {
        Stack<Integer> iterableV = new Stack<>();
        iterableV.push(v);
        Stack<Integer> iterableW = new Stack<>();
        iterableW.push(w);
        return computeAncestor(iterableV, iterableW);
    }

    private Ancestor computeAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfsFromV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfsFromW = new BreadthFirstDirectedPaths(digraph, w);

        Ancestor ancestor = new Ancestor();
        ancestor.vertex = -1;
        ancestor.dist = Integer.MAX_VALUE;

        for (int vertex = 0; vertex < digraph.V(); vertex++) {
            if (isAnAncestor(vertex, bfsFromV, bfsFromW)) {
                int distToVertex = bfsFromV.distTo(vertex) + bfsFromW.distTo(vertex);
                if (distToVertex < ancestor.dist) {
                    ancestor.vertex = vertex;
                    ancestor.dist = distToVertex;
                }
            }
        }
        if (ancestor.dist == Integer.MAX_VALUE)
            ancestor.dist = -1;
        return ancestor;
    }

    private boolean isAnAncestor(int vertex, BreadthFirstDirectedPaths bfsFromV, BreadthFirstDirectedPaths bfsFromW) {
        return bfsFromV.hasPathTo(vertex) && bfsFromW.hasPathTo(vertex);
    }

    private class Ancestor {
        private int vertex;
        private int dist;
    }
}