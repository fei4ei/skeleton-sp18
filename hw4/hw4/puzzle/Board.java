package hw4.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board implements WorldState {
    /** Constructs a board from a N-by-N array of tiles
     * where tiles[i][j] = tile at row i, column j
     * @param tiles
     */
    private int size; // size = N
    int[][] Tiles;
    int[][] Goal;
    private final int BLANK = 0; // this is the blank square in the board

    public Board(int[][] tiles) {
        size = tiles.length;
        Tiles = CopyTile(tiles);
        Goal = new int[size][size];
        GoalMaker(size);
    }

    private int[][] CopyTile(int[][] tiles) {
        int[][] myTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
//            for (int j = 0; j < size; j++) {
//                myTiles[i][i] = tiles[i][j]; //FF: why is it not working???
//            }
            System.arraycopy(tiles[i], 0, myTiles[i], 0, size);
        }
        return myTiles;
    }

    /** Generate an N-by-N array as the Goal; if N = 3, Goal =
     *
     * @param N
     */
    private void GoalMaker(int N) {
        int filler = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Goal[i][j] = filler;
                filler += 1;
            }
        }
        Goal[size-1][size-1] = BLANK;
    }

    /** Return index [i, j] of a value in the Goal array
     *
     */
    private int[] indexFinder(int value) {
        checkValueValidity(value);
        if (value == 0) {
            return new int[]{size - 1, size - 1};
        }
        int rowNum;
        int colNum;
        colNum = (value - 1) % size;
        rowNum = (value - 1) / size;
        return new int[]{rowNum, colNum};
    }

    private void checkValueValidity(int value) {
        if (value > size*size - 1 || value < 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    /** Returns value of tile at row i, column j(or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        checkValueValidity(Tiles[i][j]);
        return Tiles[i][j];
    }

    /** REtrns the board size N
     */
    public int size() {
        return size;
    }

    /** Estimated distance to goal. This method should simply
     * return the results of manhattan()
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /** Return the neighbors of the current board
     * @Source http://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug; // bug: row num of BLANK in Tiles
                    zug = tug; // zug: col num of BLANK in Tiles
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(l11il - bug) + Math.abs(lil1il1 - zug) - 1 == 0) { // found a neighbor
//                    int[][] copy = new int[hug][hug];
//                    copy = ili1li1.clone();
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug]; // problem: this will change the array just enqueued in neighbors
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
    /** Hamming distance estimate: the number of tiles in the wrong position
     */
    public int hamming() {
        int Hdist = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Tiles[i][j] == BLANK) {
                    continue;
                }
                if (Goal[i][j] != Tiles[i][j]) {
                    Hdist += 1;
                }
            }
        }
        return Hdist;
    }

    /** Manhattan distance estimate: the sum of vertical and horizontal distance from the tiles to their goal positions
     *
     * @return
     */
    public int manhattan() {
        int Mdist = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Tiles[i][j] == BLANK) {
                    continue;
                }
                if (Goal[i][j] != Tiles[i][j]) {
                    int value = Tiles[i][j];
                    int[] index = indexFinder(value);
//                    System.out.println(Tiles[i][j] + " @ Goal: " + Arrays.toString(index));
//                    System.out.println(Tiles[i][j] + " @ Tile: [" + i + ", " + j + "]");
                    Mdist += Math.abs(index[0] - i) + Math.abs(index[1] - j);
                }
            }
        }
        return Mdist;
    }

    /** Returns true if this board's tile values are the
     * position as y's
     * @param y
     * @return
     */
    public boolean equals(Object y) {
        if (this == y) return true;
        if (this.getClass() != y.getClass()) return false;
        Board nb = (Board) y;
        if (this == null && y != null) return false;
        if (this != null && y == null) return false;
        if (this == null && y == null) return true;
        if (nb.size != this.size) return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.Tiles[i][j] != nb.Tiles[i][j] || this.Goal[i][j] != nb.Goal[i][j]) return false;
            }
        }
        return true;
    }

    /** Returns the string representation of the board. 
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public String GoaltoString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", Goal[i][j]));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


}
