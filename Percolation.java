
/*
 * The model. We model a percolation system using an N-by-N grid of sites. 
 * Each site is either open or blocked. A full site is an open site that can be connected
 * to an open site in the top row via a chain of neighboring (left, right, up, down) 
 * open sites. We say the system percolates if there is a full site in the bottom row. 
 * In other words, a system percolates if we fill all open sites connected to the top row
 * and that process fills some open site on the bottom row. 
 * 
 */

public class Percolation {
	private static final int OPEN = 1;
	private static final int CLOSED = 0;
	private int gridSize = 0; //Dimension of the N by N grid used in the simulation
	private int[][] grid; 
	private int topIndex;    // Index of extra site modelling top of grid
	private int bottomIndex; // Index of extra site modelling bottom of grid
	private WeightedQuickUnionUF wqufHelper = null;

	public  Percolation(int gridSize ) {
		/*
		 * Initialize two dimensional array of integers
		 * with default values: all sites are CLOSED (0).
		 * The WeightedQuickUnionUF object is initialized to a size that will
		 * contain one element for each site in the grid (N*N), plus two more
		 * to represent "imaginary" sites at the top and bottom of the grid
		 * that will make it easier to test the grid for percolation.
		 * @param n the number of rows and column in the grid
		 */
		if (gridSize <= 0) throw (new IllegalArgumentException("Invalid grid Size."));
		this.gridSize = gridSize;
		grid = new int[gridSize][gridSize];
		wqufHelper = new WeightedQuickUnionUF((gridSize*gridSize) + 2);
		topIndex = 0;
		bottomIndex = gridSize * gridSize + 1;
		
		for (int i = 0; i < gridSize; i++)
			for (int j = 0; j < gridSize; j++)
				grid[i][j] = CLOSED; //close site
	}
	
	
	/**
     * Open site (row i, column j) by setting site to OPEN
     * and calling the union method in the union-find object
     * for any open adjacent sites.
     *
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @throws  IndexOutOfBoundsException if the values for i and j are 
     *          off the grid
     */
	public void open(int i, int j) {
		int row = i - 1;
		int col = j - 1;
		
		checkIndices(row, col);
		grid[row][col] = OPEN; // open the site in row, col pos.
		
		int index = getPos(row,col);
		
		if (row == 0) //Is the Top?, then connect to the imaginary site at 0
			wqufHelper.union(topIndex, index);
		
		if (row == gridSize - 1) //It is on the Bottom? then connect to the imaginary site at (N*N)+1
			wqufHelper.union(bottomIndex, index);
		
		if ((row + 1) < gridSize ) //We want not like fall off the grid
			if (grid[row+1][col] == OPEN)
				wqufHelper.union(index, getPos(row+1, col));		
		
		if ((row - 1) >= 0) //We want not like fall off the grid
			if (grid[row-1][col] == OPEN)
				wqufHelper.union(index,getPos(row-1, col));		
		
		if ((col + 1) < gridSize) //We want not like fall off the grid
			if (grid[row][col + 1] == OPEN)
				wqufHelper.union(index, getPos(row, col+1));
		
		if ((col -1 ) >= 0) //We want not like fall off the grid
			if (grid[row][col - 1] == OPEN)
				wqufHelper.union(index, getPos(row, col-1));		
			
	}
	//is the site at position i,j Open?	
	public boolean isOpen(int i, int j){
		int row = i - 1;
		int col = j - 1;
		checkIndices(row, col);
		return grid[row][col] == OPEN;
	}
	
	//is the site at position i,j Closed?	
	public boolean isFull(int i, int j){
		return !(isOpen(i,j));
	}
	
	/**
    * Does the system percolate? Checks to see if the imaginary site
    * at location 0 in the union-find object is in the same set as 
    * the imaginary site at location N*N+1 in the union-find object.
    * These two sites are imaginary; they are not actually represented
    * in the grid, although they are represented in the union-find
    * object. They are "located" at the top and the bottom of the 
    * grid, and each connects to all the sites above/below it.
    *
    * @return true if open path from the bottom of the grid to the top exists
    */
	public boolean percolates(){
		return wqufHelper.connected(topIndex, bottomIndex);
	}
	
    /**
     * Converts 2D point to 1D array location for the union-find object
     * to use.
     *
     * The +1 compensates for the site at the
     * beginning used to facilitate the 
     * percolation algorithm. (There is a
     * corresponding single site added to the
     * end that does not affect this
     * calculation.)
     * @param i the integer representing the site location on the x axis
     * @param j the integer representing the site location on the y axis
     * @return  the location in the union-find object where the site
     *          should be stored
     * @throws  IndexOutOfBoundsException if the values for i and j are 
     *          off the grid
     */
	private int getPos(int i, int j){
		checkIndices(i, j);
		return ((i*gridSize)+j) + 1;
		
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
	  
	if (i < 0 || i > this.gridSize)   
		throw new IndexOutOfBoundsException("row index " + i
				  + " must be between 0 and "
				  + (gridSize));  
	
    if (j < 0 || j > gridSize)
      throw new IndexOutOfBoundsException("column index "
					  + j + " must be between 0 and "
					  + (gridSize));
  }
  
	
	
	public static void main(String[] args) {
        final int TESTS = 1000;
        final int GRID_SIZE = 20;
         
        System.out.println("\n***Percolation: Monte Carlo Simulation ***\n");
         
        Percolation perc = new Percolation(GRID_SIZE);
        System.out.println("Successfully created Percolation object.");
        System.out.println("N: " + perc.gridSize);
        System.out.println();
         
        System.out.println("Starting to open random sites...");
        int row, col, ct;
        double sum = 0.0;
        for (int i = 0; i < TESTS; i++) {
            ct = 0;
            perc = new Percolation(GRID_SIZE);
            while (!perc.percolates()) {
                row = StdRandom.uniform(perc.gridSize) + 1;
                col = StdRandom.uniform(perc.gridSize) + 1;
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
