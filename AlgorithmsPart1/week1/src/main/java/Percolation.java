public class Percolation {
    private int nbOfSites;
    private boolean[][] sitesStatus;
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
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        validateParameters(i, j);

        if (!isOpen(i, j)) {
            sitesStatus[i][j] = true;
            int currentSite = nbOfSites * (i - 1) + j - 1;
            if (i == 1)
                weightedQuickUnionUF.union(currentSite, virtualTopSite);
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
        int currentSite = nbOfSites * (i - 1) + j - 1;
        return weightedQuickUnionUF.connected(currentSite, virtualTopSite);
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
}