public class Percolation {
    private int nbOfSites;
    private boolean[][] sitesStatus;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new IndexOutOfBoundsException();
        nbOfSites = N;
        weightedQuickUnionUF = new WeightedQuickUnionUF(nbOfSites * nbOfSites);
        sitesStatus = new boolean[nbOfSites][nbOfSites];
        for (int i = 0; i < nbOfSites; i++) {
            for (int j = 0; j < nbOfSites; i++) {
                sitesStatus[i][j] = false;
            }
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        weightedQuickUnionUF.union(i, j);
        sitesStatus[i - 1][j - 1] = true;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return siteStatus(i, j);
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return siteStatus(i, j);
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <= nbOfSites; i++) {
            if (isOpen(1, i)) {
                for (int j = 1; j <= nbOfSites; j++) {
                    if (isOpen(nbOfSites, j)) {
//                        if (weightedQuickUnionUF.connected())
                    }
                }
            }
        }
        return false;
    }

    private boolean siteStatus(int i, int j) {
        return sitesStatus[i - 1][j - 1];
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}