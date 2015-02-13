public class PercolationStats {

    private double[] thresholds;
    private  int N;
    private  int T;


    public PercolationStats(int N, int T) {
        this.N = N;
        this.T = T;

        if (this.N <= 0 || this.T <= 0) {
            throw new IllegalArgumentException();
        }

        Percolation percolation;

        thresholds = new double[this.T];

        for (int t = 0; t < this.T; t++) {

            int n = 0;
            percolation = new Percolation(this.N);

            while(n < N * N) {

                int i = StdRandom.uniform(this.N) + 1;
                int j = StdRandom.uniform(this.N) + 1;

                if (percolation.isOpen(i, j))
                    continue;

                n++;
                percolation.open(i, j);

                if (percolation.percolates()) {
                    thresholds[t] = n / (double) (this.N * this.N);
                    break;
                }
            }
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }


    public double confidenceLo(){
        return mean() - ((1.96 * stddev()) / Math.sqrt(this.T));

    }

    public double confidenceHi(){
        return mean() + ((1.96 * stddev()) / Math.sqrt(this.T));
    }


    public static void main(String[] args) {
        final int N = Integer.parseInt(args[0]);
        final int T = Integer.parseInt(args[1]);
        Stopwatch sw = new Stopwatch();


        PercolationStats stats = new PercolationStats(N, T);

        double t = sw.elapsedTime();

        double mean = stats.mean();
        double stddev = stats.stddev();
        double intervallo = stats.confidenceLo();
        double intervalhi = stats.confidenceHi();

        System.out.println("time used               = " + t + "s");
        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + stddev);
        System.out.println("95% confidence interval = " + intervallo + ", " + intervalhi);
    }
}
