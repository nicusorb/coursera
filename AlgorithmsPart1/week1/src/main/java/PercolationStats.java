public class PercolationStats {
    private double mean;
    private double standardDeviation;
    private double confidenceLo, confidenceHi;
    private double[] fractionOfOpenedSites;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();
        fractionOfOpenedSites = new double[T];

        for (int i = 0; i < T; i++) {
            fractionOfOpenedSites[i] = simulatePercolation(N) / ((double) N * N);
        }

        mean = StdStats.mean(fractionOfOpenedSites);
        standardDeviation = StdStats.stddev(fractionOfOpenedSites);
        confidenceLo = mean - 1.96 * standardDeviation / Math.sqrt(T);
        confidenceHi = mean + 1.96 * standardDeviation / Math.sqrt(T);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return standardDeviation;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    private int simulatePercolation(int N) {
        Percolation percolation = new Percolation(N);
        int nbOfOpenedSites = 0;
        while (!percolation.percolates()) {
            int site1 = generateRandomSite(N);
            int site2 = generateRandomSite(N);
            if (percolation.isFull(site1, site2)) {
                percolation.open(site1, site2);
                nbOfOpenedSites++;
            }
        }
        return nbOfOpenedSites;
    }

    private int generateRandomSite(int N) {
        int site = 0;
        while (site == 0)
            site = StdRandom.uniform(N + 1);
        return site;
    }

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
//        int N = 2;
//        int T = 100000;

        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}