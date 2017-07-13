///////////////////////////////////////////////////////////////////////////
// Created by Arundhati Saha, KIIT University, 1405205
// Dated: 13th July, 2017
///////////////////////////////////////////////////////////////////////////

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] exps;
    public PercolationStats(int n, int trials) { // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Invalid args");
        this.trials = trials;
        exps = new double[trials];
        Percolation map;
        for (int i = 0; i < trials; i++) { // T independent trials
            map = new Percolation(n);
            while (!map.percolates())
                map.open((StdRandom.uniform(n)+1), (StdRandom.uniform(n)+1));
            exps[i] = (map.numberOfOpenSites()/(double) (n*n));
        }
    }
    public double mean() { // sample mean of percolation threshold
        return StdStats.mean(exps);
    }
    public double stddev() { // sample standard deviation of percolation threshold
        return StdStats.stddev(exps);
    }
    public double confidenceLo() { // low  endpoint of 95% confidence interval
        return (mean() - (1.96*stddev()/Math.sqrt(trials)));
    }
    public double confidenceHi() { // high endpoint of 95% confidence interval
        return (mean() + (1.96*stddev()/Math.sqrt(trials)));
    }
    public static void main(String[] args) { // test client (described below)
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stat = new PercolationStats(n, trials);
        StdOut.println("mean\t\t\t= "+stat.mean());
        StdOut.println("stddev\t\t\t= "+stat.stddev());
        StdOut.println("95% confidence interval = ["+stat.confidenceLo()+", "+stat.confidenceHi()+"]");
    }
}
