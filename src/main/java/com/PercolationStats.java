public class PercolationStats {
  private double[] results;

  public PercolationStats(int N, int T) {
    results = new double[T]; 

    for (int i = 0; i < T; i++) {
      Percolation p = new Percolation(N);
      int sitesOpen = 0;

      do {
        int[] coords = pickOpenCell(p, N);
        p.open(coords[0], coords[1]);
        sitesOpen += 1;
      } while (!p.percolates());

      results[i] = (double) sitesOpen / (N*N);
    }
  }

  public double mean()   { return StdStats.mean(results); }
  public double stddev() { return StdStats.stddev(results); }

  private String confidenceInterval(double mean, double stddev, int t) {
    double start = mean - 1.96*stddev/Math.sqrt(t);
    double end = mean + 1.96*stddev/Math.sqrt(t);

    return Double.toString(start) + ".." + Double.toString(end);
  }

  public static void main(String[] args) {
    int N = new Integer(args[0]);
    int T = new Integer(args[1]);
    if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException();

    PercolationStats p = new PercolationStats(N, T);
    double mean = p.mean();
    double stddev = p.stddev();
    String confidenceInterval = p.confidenceInterval(mean, stddev, T);
    System.out.format("mean                     = %f\n", mean);
    System.out.format("stddev                   = %f\n", stddev);
    System.out.println("95% confidence interval  = " + confidenceInterval);
  } 

  private int[] pickCell(int n) {
    int i   = StdRandom.uniform(n)+1;
    int j   = StdRandom.uniform(n)+1;
    int[] coords = new int[2];
    coords[0] = i;
    coords[1] = j;

    return coords;
  }

  private int[] pickOpenCell(Percolation p, int n) {
    int[] coords;
    boolean success = false;

    do {
      coords = pickCell(n);
      if (!p.isOpen(coords[0], coords[1])) {
        p.open(coords[0], coords[1]);
        success = true;
      }
    } while (!success);


    return coords;
  }
}
