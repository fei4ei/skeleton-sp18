package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

import javax.xml.stream.FactoryConfigurationError;
import java.util.HashSet;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    // page 547 textbook
    private boolean cycleFound = false;
    private int[] myedgeTo;
    private Stack<Integer> s = new Stack<>();
    private HashSet h = new HashSet();

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        distTo[0] = 0;
        edgeTo[0] = 0;
        myedgeTo = new int[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            myedgeTo[i] = Integer.MAX_VALUE;
        }
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfsCycle(0, 0);
    }

    public boolean hasCycle() {
        return cycleFound;
    }

    // v: current vertex; u: parent vertex of v
    private void dfsCycle(int v, int u) {
        marked[v] = true;
        announce();
        if (cycleFound == true) {
            return;
        }

        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                myedgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                dfsCycle(w, v);
            }
            else if (w != u) { // w is already visited but the parent of v
                cycleFound = true;
                myedgeTo[w] = v;
                for (int i = w; !h.contains(i); i = myedgeTo[i]) {
                    edgeTo[i] = myedgeTo[i];
                    h.add(i);
                }
                announce();
                return;
            }
        }

//        if (marked[v] == true) { // base case
//            cycleFound = true;
//        }
//
//        if (cycleFound) {
//            announce();
//            return;
//        }
//
//        marked[v] = true;
//        announce();
//
//        for (int w : maze.adj(v)) { // recursion
//            if (!marked[w]) {
//                dfsCycle(w);
//            }
//        }
    }
}

