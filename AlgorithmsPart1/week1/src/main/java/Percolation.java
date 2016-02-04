import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int nbOfSites;
    private boolean[][] sitesStatus;
    private boolean[][] fullStatus;
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int virtualTopSite, virtualBottomSite;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        nbOfSites = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(nbOfSites * nbOfSites + 2);
        virtualTopSite = nbOfSites * nbOfSites;
        virtualBottomSite = nbOfSites * nbOfSites + 1;
        sitesStatus = new boolean[nbOfSites + 1][nbOfSites + 1];
        fullStatus = new boolean[nbOfSites + 1][nbOfSites + 1];
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validateParameters(i, j);

        if (!isOpen(i, j)) {
            sitesStatus[i][j] = true;
            int currentSite = nbOfSites * (i - 1) + j - 1;
            if (i == 1) {
                weightedQuickUnionUF.union(currentSite, virtualTopSite);
                setFull(i, j);
            }
            if (i == nbOfSites)
                weightedQuickUnionUF.union(currentSite, virtualBottomSite);
            if (i > 1 && isOpen(i - 1, j))
                weightedQuickUnionUF.union(currentSite, nbOfSites * (i - 2) + j - 1);
            if (i < nbOfSites && isOpen(i + 1, j))
                weightedQuickUnionUF.union(currentSite, nbOfSites * i + j - 1);
            if (j > 1 && isOpen(i, j - 1))
                weightedQuickUnionUF.union(currentSite, nbOfSites * (i - 1) + j - 2);
            if (j < nbOfSites && isOpen(i, j + 1))
                weightedQuickUnionUF.union(currentSite, nbOfSites * (i - 1) + j);

            if (hasFullNeighbors(i, j)) {
                setFull(i, j);
            }
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        validateParameters(i, j);
        return sitesStatus[i][j];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        validateParameters(i, j);
        return fullStatus[i][j];
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualTopSite, virtualBottomSite);
    }

    // test client (optional)
    public static void main(String[] args) {
    }

    private void validateParameters(int i, int j) {
        if (i < 1 || i > nbOfSites || j < 1 || j > nbOfSites)
            throw new IndexOutOfBoundsException();
    }

    private boolean hasFullNeighbors(int i, int j) {
        return (j > 1 && isFull(i, j - 1)) || (j < nbOfSites && isFull(i, j + 1))
                || (i > 1 && isFull(i - 1, j)) || (i < nbOfSites && isFull(i + 1, j));
    }

    private void setFull(int i, int j) {
        fullStatus[i][j] = true;
        if (shouldSetFull(i, j - 1))
            setFull(i, j - 1);
        if (shouldSetFull(i, j + 1))
            setFull(i, j + 1);
        if (shouldSetFull(i - 1, j))
            setFull(i - 1, j);
        if (shouldSetFull(i + 1, j))
            setFull(i + 1, j);
    }

    private boolean shouldSetFull(int i, int j) {
        return i >= 1 && i <= nbOfSites && j >= 1 && j <= nbOfSites && isOpen(i, j) && !isFull(i, j);
    }
}