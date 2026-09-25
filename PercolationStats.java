import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class PercolationStats {
    public final int trialCount;
    public final double sampleMean;
    public final double sampleStddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Size + num of trials must be positive");
        }
        trialCount = trials;
        double[] thresholds = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            Percolation grid = new Percolation(n);
            while (!grid.percolates()) {
                int row= StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                grid.open(row, col);
            }
            thresholds[trial] = grid.numberOfOpenSites() / ((double) n * n);
        }
        sampleMean =StdStats.mean(thresholds);
        sampleStddev = StdStats.stddev(thresholds);
    }

    public double mean() {
        return sampleMean;
    }

    public double stddev() {
        return sampleStddev;
    }

    public double confidenceLo() {
        return sampleMean - 1.96* sampleStddev / Math.sqrt(trialCount);
    }

    public double confidenceHi() {
        return sampleMean + 1.96* sampleStddev / Math.sqrt(trialCount);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println("mean: " + stats.mean());
        System.out.println("stddev: "+ stats.stddev());
        System.out.println("95% confidence interval: [" + stats.confidenceLo()+ ", " + stats.confidenceHi() + "]");
    }
}
