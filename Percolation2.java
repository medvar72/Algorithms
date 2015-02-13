/**
 * This class tests an N by N grid to see if there are enough open
 * locations to connect the top of the grid with the bottom of the
 * grid. It uses a two dimensional array of integers to represent
 * the grid, and a GridSet object
 * to keep track of the connections between open sites in the grid.
 *
 * The percolates() method returns true if a path can be found from a
 * site at the top to the bottom. These two sites are are not actually
 * represented in the grid, although they are represented in the 
 * GridSet object at locations N*N and N*N + 1. They are "located"  
 * above and below the grid, and each connects to all the sites 
 * immediately above or below it. This simplifies the pathfinding
 * process.
 *
 * Dependencies: GridSet.java, StdRandom.java,
 */

public class Percolation2 {
  
  private static final int BLOCKED = 0; // Site status blocked
  private static final int OPEN = 1;    // Site status open
  private int N;                        // Side length of NxN grid
  private int[][] grid;                 // Which sites are blocked/open
  private int topIndex;    // Index of extra site modelling top of grid
  private int bottomIndex; // Index of extra site modelling bottom of grid
  private WeightedQuickUnionUF gs;      // GridSet object
    
  /**
   * Constructor initializes two dimensional array of integers
   * with default values: all sites set to BLOCKED (0).
   *
   * The GridSet object is initialized to a size that will
   * contain one element for each site in the grid (N*N), plus two more
   * to represent "imaginary" sites at the top and bottom of the grid
   * that will make it easier to test the grid for percolation.
   *
   * @param n the number of rows and column in the grid
   */
  public Percolation2(int n) {
    N = n;
    grid = new int[N][N];
    gs = new WeightedQuickUnionUF((N * N) + 2);
    topIndex = 0;
    bottomIndex = N * N + 1;
	
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
	grid[i][j] = BLOCKED;
  }

    /**
     * Open site (row i, column j) by setting site to OPEN
     * and calling the connect method in the GridSet object
     * for any open adjacent sites.
     *
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @throws  IndexOutOfBoundsException if the values for i and j are 
     *          off the grid
     */
  public void open(int i, int j) {
    int row = i;
    int col = j;

    checkIndices(row, col);
    grid[row][col] = OPEN; // mark site as open

    /* connect site with any open neighbours */
    int index = getIndex(row, col);

    if (row == 0) { // If it's on the top row, connect to virtual top site.
      gs.union(topIndex, index);
    }
    if (row == N - 1) { // If it's on bottom row,
                        // connect to virtual bottom site
      gs.union(bottomIndex, index);
    }
    if ((row + 1) < N) { // Make sure we don't fall off the grid
      if (grid[row+1][col] == OPEN)
	gs.union(index, getIndex(row+1, col));
    }
    if ((row - 1) >= 0) { // Make sure we don't fall off the grid
      if (grid[row-1][col] == OPEN)
	gs.union(index, getIndex(row-1, col));
    }
    if ((col + 1) < N) { // Make sure we don't fall off the grid
      if (grid[row][col+1] == OPEN)
	gs.union(index, getIndex(row, col+1));
    }
    if ((col - 1) >= 0) { // Make sure we don't fall off the grid
      if (grid[row][col-1] == OPEN)
	gs.union(index, getIndex(row, col-1));
    }
  }
    
    /**
     * Is site (row i, column j) open?
     *
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @return  true if the site at (i, j) is OPEN
     * @throws  IndexOutOfBoundsException if the values for i and j are 
     *          off the grid
     */
  public boolean isOpen(int i, int j) {
    checkIndices(i, j);
    return grid[i][j] == OPEN;
  }
    
    /**
     * Is site (row i, column j) full?
     *
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @return  true if the site at (i, j) is FULL
     * @throws  IndexOutOfBoundsException if the values for i and j are 
     *          off the grid
     */
  public boolean isFull(int i, int j) {
    checkIndices(i, j);
//    return gs.connected(topIndex, getIndex(i, j));
    return grid[i][j] == BLOCKED;
  }
    
    /**
     * Does the system percolate? Checks to see if the imaginary site
     * at location N*N in the GridSet object is in the same set as 
     * the imaginary site at location N*N+1 in the GridSet object.
     * These two sites are imaginary; they are not actually represented
     * in the grid, although they are represented in the GridSet
     * object. They are "located" at the top and the bottom of the 
     * grid, and each connects to all the sites above/below it.
     *
     * @return true if open path from the bottom of the grid to the top exists
     */
  public boolean percolates() {
    return gs.connected(topIndex, bottomIndex);
  }
    
    /**
     * Converts 2D point to 1D array location for the GridSet object
     * to use.
     *
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @return  the location in the GridSet object where the site
     *          should be stored
     */
  private int getIndex (int i, int j) {
    return i*N + j;
  }
  
  /**
     * Check that site (row i, column j) is on the grid
     *
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @throws  IndexOutOfBoundsException if the values for i and j are 
     *          off the grid
     */
  private void checkIndices (int i, int j) {
    if (i < 0 || i >= N)
      throw new IndexOutOfBoundsException("row index " + i
					  + " must be between 0 and "
					  + (N-1));
    if (j < 0 || j >= N)
      throw new IndexOutOfBoundsException("column index "
					  + j + " must be between 0 and "
					  + (N-1));
  }
  
  public static void main(String[] args) {
      final int TESTS = 1000;
      final int GRID_SIZE = 20;
       
      System.out.println("\n***Percolation: Monte Carlo Simulation ***\n");
       
      Percolation2 perc = new Percolation2(GRID_SIZE);
      System.out.println("Successfully created Percolation object.");
      System.out.println("N: " + perc.N);
      System.out.println();
       
      System.out.println("Starting to open random sites...");
      int row, col, ct;
      double sum = 0.0;
      for (int i = 0; i < TESTS; i++) {
          ct = 0;
          perc = new Percolation2(GRID_SIZE);
          while (!perc.percolates()) {
              row = StdRandom.uniform(perc.N);
              col = StdRandom.uniform(perc.N);
              if (perc.isFull(row, col)) {
                  perc.open(row, col);
                  ct++;
              }
          }
          sum += ct;
      }
      System.out.println("Tests " + TESTS + " attempts, the average number of sites");
      System.out.printf("opened was %.2f", sum/TESTS);
      System.out.printf(" or %.2f", ((sum/TESTS)/(GRID_SIZE * GRID_SIZE)) * 100);
      System.out.println("%.");
      System.out.println("\nDone.\n");
  }

  
}