package hw2;

import java.util.Random;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

import static edu.princeton.cs.introcs.StdRandom.uniform;
import static edu.princeton.cs.introcs.StdStats.mean;
import static edu.princeton.cs.introcs.StdStats.stddev;

public class PercolationStats {
    private int num_of_exp;
    private Random rand = new Random();
    private int rand_int;
    private double[] num_opensite;
    private Percolation[] perc;
    private double mean;
    private double stddev;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        num_of_exp = T;
        num_opensite = new double[T];
        perc = new Percolation[T];
        double sum = 0;
        for (int i = 0; i < T; i++) {
            perc[i] = pf.make(N);
            while (!perc[i].percolates()) {
                // generate a random number between [0, N*N)
                rand_int = uniform(0, N*N);
//                rand_int = rand.nextInt(N*N);
                if (!perc[i].isOpen(rand_int)) {
                    perc[i].open(rand_int);
                }
            }
            num_opensite[i] = perc[i].numberOfOpenSites();
//            sum += num_opensite[i];
        }
        mean = edu.princeton.cs.introcs.StdStats.mean(num_opensite) / (N*N);
        stddev = edu.princeton.cs.introcs.StdStats.stddev(num_opensite) / (N*N);
//        mean = sum / (N*N*T);
//        stddev = Math.sqrt(variance(mean)) / (N*N);
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
        return mean - 1.96*stddev/Math.sqrt(num_of_exp);
    }

    public double confidenceHigh() {
        return mean + 1.96*stddev/Math.sqrt(num_of_exp);
    }

    /** use for unit testing
     *
     * @param args
     */
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(5, 50, pf);
//        System.out.println(ps.mymean());
        System.out.println(ps.mean);
//        System.out.println(ps.mystddev());
        System.out.println(ps.stddev);
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
