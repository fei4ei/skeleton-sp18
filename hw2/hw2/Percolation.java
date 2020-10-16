package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // int[][] grid;
    // for the opentracker: 0: closed; 1: open; 2: full;
    WeightedQuickUnionUF grid;
    int[] openTracker;
    int n;

    private int xyto1D(int x, int y) {
        return (x*n + y);
    }

    private int[] oneDtoxy(int index) {
        int x = index % n;
        int y = index / n;
        int[] oneD =  {x,y};
        return oneD;
    }

    /** create N-by-N grid, with all sites initially blocked
     * @param N: size of the grid
     */
    public Percolation(int N) {
        // grid = new int[N][N];
        n = N;
        grid = new WeightedQuickUnionUF(N*N+3);
        // N*N+2: dummy open site at N*N, virtual top site at N*N+1, virtual bottom site at N*N+2
        // openTracker = new int[N*N+3];
        // virtual top site is always full.
        // openTracker[N*N] = 1;
        // Initial status of the grid: all sites are closed, virtual bottom site is closed.
    }

    /** open the site (row, col) if it is not open already
     *
     * @param row: y
     * @param col: x
     */
    public void open(int row, int col) {
        // grid[row][col] = 1;
        int index = xyto1D(col, row);
        openTracker[index] = 1;
        // grid.union(n*n, index);
        for (int i = 0; i < findNeighbors(row, col).length; i++) {
            int[] temp = oneDtoxy(i);
            if (isOpen(temp[1], temp[0])) {
                grid.union(index, i);
            }
        }
    }

    // return the 1D index of neighboring sites. pay attention to corner cases.
    private int[] findNeighbors(int row, int col) {

        return null;
    }

    public boolean isOpen(int row, int col) {
       // return (grid[row][col] == 1);
       return (openTracker[col*n + row] == 1);
       // return grid.find(n*n) == grid.find(xyto1D(col, row));
    }

    public boolean isFull(int row, int col) {
        // return (grid[row][col] == 2);
        return (openTracker[col*n + row] == 2);
    }

    public int numberOfOpenSites() {
        int j = 0;
        for (int i = 0; i < n*n; i++) {
            if (openTracker[i] == 1) {
                j++;
            }
        }
        return j;
    }

    /** Does the system percolate?
     *
     */
    public boolean percolates() {
        return false;
    }

    /** use for unit testing
     *
     * @param args
     */
    public static void main(String[] args) {

    }

}
