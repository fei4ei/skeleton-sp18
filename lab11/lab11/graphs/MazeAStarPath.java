package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private static final int infinity = Integer.MAX_VALUE;
//    private MinPQ<Integer> fringe = new MinPQ<>();


    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    private class MazeComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer s, Integer t) {
            return Math.abs(maze.toX(s) - maze.toX(s)) + Math.abs(maze.toY(s) - maze.toY(s));
        }
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(v)) + Math.abs(maze.toY(t) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        Queue<Integer> q = new Queue<>();
        for (int v = 0; v < maze.V(); v++) {
            distTo[v] = infinity;
        }
        marked[t] = true;
        q.enqueue(t); // bfs
        while (!q.isEmpty()) {
            int curr = q.dequeue();
            for (int i : maze.adj(curr)) {
                if (!marked[i]) {
                    marked[i] = true;
                    if (i == t) {
                        return i;
                    }
                }
            }
        }
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        MinPQ<Integer> fringe = new MinPQ(new MazeComparator());
        fringe.insert(s);
        marked[s] = true;
        while (!fringe.isEmpty()) {
            int curr = fringe.delMin();
            if (curr == t) {
                announce();
                break;
            }
            for (int i : maze.adj(curr)) {
                if(!marked[i]) {
                    fringe.insert(i);
                    distTo[i] = distTo[curr] + 1;
                    edgeTo[i] = curr;
                    marked[i] = true;
                }
            }
        }

    }

    @Override
    public void solve() {
        astar(s);
    }

}

