package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import static hw4.puzzle.SearchNode.SNComparator;

public class Solver {
    /** Constructor which solves the puzzle, computing everything necessary
     * for moves() and solution() to not have to solve the problem again.
     * Solves the puzzle using the A* algorithm. Assumes a solution exists.
    */
    int NumOfMoves = -1;
    SearchNode target = null;
    MinPQ<SearchNode> fringe;
    int TotalEnqueued = 0;

    public Solver(WorldState Start_Goal) {
        fringe = SearchNode.createPQ(Start_Goal);

        while (!fringe.isEmpty()) {
            SearchNode curr = fringe.delMin();
            if (curr.isGoal()) {
                NumOfMoves = curr.NumberOfMoves;
                target = curr;
                break;
            }
            for (WorldState w : curr.neighbors()) {
                SearchNode nb = new SearchNode(w, curr.NumberOfMoves + 1, curr);
                if (!nb.wsEquals(curr.Prev)) { // optimization: don't add the grandparents
                    fringe.insert(nb);
                    TotalEnqueued += 1;
                }
            }
        }
    }

    /** Return the minimal number of moves to solve the puzzle at the initial WorldState
     */
    public int moves() {
        return NumOfMoves; // return target.NumberOfMoves
    }

    public Iterable<WorldState> solution() {
        if (target == null) {
            return null;
        }
        return solution(target);
    }
    /** Return a sequence of WorldStates from the initial WorldState to the solution
     */
    private Iterable<WorldState> solution(SearchNode goal) {
        Stack<WorldState> path = new Stack<>();
        for (SearchNode n = goal; n != null; n = n.Prev) {
            path.push(n.ws);
        }
        return path;
    }

}
