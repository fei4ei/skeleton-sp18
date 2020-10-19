package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// x pointing right and y pointing down; row and col number of the top left site is [0,0]

public class Percolation {
    // int[][] grid;
    // for the opentracker: 0: closed; 1: open; 2: full;
    private WeightedQuickUnionUF grid;
    private int[] openTracker; // tracks the status of open vs. closed for each site in the grid
    // Tuo used a class for each site with three variable: int row, int column, boolean open
    private int n; // this is the length of each side, i.e., the grid is n*n
    private boolean percolate; // track whether the grid percolates or not
    private int countOpen; // track the number of open sites in the grid

    // convert row (y) and col (x) to 1d index
    private int xyto1D(int x, int y) {
        if ((x < 0) || (y < 0) || (x >= n) || (y >= n)) {
            return -1;
        }
        return (x*n + y);
    }

    private int[] oneDtoxy(int index) {
        if ((index < 0) || (index >= n*n)) {
            return new int[]{-1, -1};
        }
        int col = index % n;
        int row = index / n;
        return new int[]{row, col};
    }


    /** create N-by-N grid, with all sites initially blocked
     * @param N: size of the grid
     */
    public Percolation(int N) {
        // grid = new int[N][N];
        n = N;
        grid = new WeightedQuickUnionUF(N*N+2);
        // N*N+1: dummy open site at N*N+2 (turns out to be problematic. Not implemented)
        // virtual top site at N*N
        // virtual bottom site at N*N+1 (turns out to be not used. Used Union find instead).

        openTracker = new int[N*N+1];
        // virtual top site is always full.
        openTracker[N*N] = 1;
        // Initial status of the grid: all sites are closed, virtual bottom site is closed.
        percolate = false;
        countOpen = 0;
//        bottomFull = false;
    }

    void open(int index) {
        int[] coord = oneDtoxy(index);
        open(coord[0], coord[1]);
    }

    /** open the site (row, col) if it is not open already
     *
     * @param row: y
     * @param col: x
     */
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        // grid[row][col] = 1;
        int index = xyto1D(col, row);
        openTracker[index] = 1;
        countOpen += 1;
        // grid.union(n*n, index);
        /* An initial design that connects a dummy open site with the current site
        leads to an unsolvable problem of prematurely connecting sites, and is not pursued.
         */

        // union the current site with its open neighboring sites
        int[] Neighbors = findNeighbors(row, col);
        for (int i = 0; i < Neighbors.length; i++) {
            if (Neighbors[i] < 0) { // Neighbor[i] == -1 indicates out of bounds neighbors
                continue;
            }
            if (isOpen(Neighbors[i])) {
                grid.union(index, Neighbors[i]);
            }
        }

        // open sites of top rows are connected to the virtual top site
        if (row == 0) {
            grid.union(n*n, index);
        }

        // if a site in the bottom row is opened, it is connected to the virtual bottom site;
        // this design is problematic: only open the bottom row will "percolate"
//        if (row == n-1 && bottomFull == false && grid.find(index) == grid.find(n*n)) {
//            bottomFull = true;
//        }
        if (row == n-1 && percolate == false) {
            grid.union(n*n+1, index);
        }

        if (isFull(n*n+1)) {
            percolate = true;
        }
        // An initial implementation leads to the "backwash" problem
//        if (row == 0) {
//            grid.union(n*n+2, index);
//        }
    }

    // return the 1D index of neighboring sites. pay attention to corner cases.
    private int[] findNeighbors(int row, int col) {
        // int[0]: col-1; int[1]: col+1; int[2]: row-1; int[2]: row+1
        int[] Neighbors =  new int[4];
        Neighbors[0] = xyto1D(col-1, row);
        Neighbors[1] = xyto1D(col+1, row);
        Neighbors[2] = xyto1D(col, row-1);
        Neighbors[3] = xyto1D(col, row+1);
        return Neighbors;
    }

    public boolean isOpen(int row, int col) {
       // return (grid[row][col] == 1);
       return isOpen(xyto1D(col, row));
       // return grid.find(n*n) == grid.find(xyto1D(col, row));
    }

    boolean isOpen(int index) {
        return (openTracker[index] == 1);
    }

    public boolean isFull(int row, int col) {
        return isFull(xyto1D(col, row));
    }

    private boolean isFull(int index) {
        return (grid.find(n*n) == grid.find(index));
    }

    // the original design is O(n*n), not O(1)
    public int numberOfOpenSites() {
//        int j = 0;
//        for (int i = 0; i < n*n; i++) {
//            if (openTracker[i] == 1) {
//                j++;
//            }
//        }
//        return j;
        return countOpen;
    }

    /** Does the system percolate?
     *
     */
    public boolean percolates() {
        // open sites of bottom rows are connected to the virtual bottom site; however, this design suffers the backwash problem
//        return (grid.find(n*n+1) == grid.find(n*n));
        for (int i = 0; i < n; i++){
            if (isFull(n-1, i)) {
                return true;
            }
        }
        return false;
//        return isFull(n*n+1);
    }

    /** use for unit testing
     *
     * @param args
     */
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(0,0);
        perc.open(1,0);
        perc.open(2,0);
        System.out.println(perc.percolates());
        System.out.println(perc.numberOfOpenSites());
    }

}
