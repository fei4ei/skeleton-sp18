package hw2;

import java.util.Random;

public class PercolationStats {
    private int num_of_exp;
    private Random rand = new Random();
    private int rand_int;
    private double[] frac_opensite;
    private Percolation[] perc;
    private double mean;
    private double stddev;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        num_of_exp = T;
        frac_opensite = new double[T];
        perc = new Percolation[T];
        double sum = 0;
        for (int i = 0; i < T; i++) {
            perc[i] = pf.make(N);
            while (!perc[i].percolates()) {
                rand_int = rand.nextInt(N*N); // generate a random number between [0, N*N)
                if (!perc[i].isOpen(rand_int)) {
                    perc[i].open(rand_int);
                }
            }
            frac_opensite[i] = perc[i].numberOfOpenSites();
            sum += frac_opensite[i];
        }
        mean = sum / (N*N*T);
        stddev = Math.sqrt(variance(mean));
    }

    public double mean() {
        return mean;
    }

    private double variance(double mean) {
        double sumDev = 0;
        for (int i = 0; i < num_of_exp; i++) {
            sumDev += Math.pow((perc[i].numberOfOpenSites() - mean), 2);
        }
        return sumDev / (num_of_exp - 1);
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return (mean - 1.96*stddev)/Math.sqrt(num_of_exp);
    }

    public double confidenceHigh() {
        return (mean + 1.96*stddev)/Math.sqrt(num_of_exp);
    }

    /** use for unit testing
     *
     * @param args
     */
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(10, 100, pf);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }


}
