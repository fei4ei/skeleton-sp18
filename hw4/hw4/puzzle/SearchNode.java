package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

public class SearchNode implements WorldState{
    WorldState ws;
    int NumberOfMoves;
    SearchNode Prev;

    public SearchNode(WorldState input) {
        ws = input;
        NumberOfMoves = 0;
        Prev = null;
    }

    public SearchNode(WorldState input, int nom, SearchNode previous) {
        ws = input;
        NumberOfMoves = nom;
        Prev = previous;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return 0;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        return null;
    }

    public static MinPQ<SearchNode> creatPQ(WorldState inputstate) {
        MinPQ<SearchNode> fringe = new MinPQ<>();
        SearchNode inputnode = new SearchNode(inputstate);
        fringe.insert(inputnode);
        return fringe;
    }

    public static void Astar(MinPQ<SearchNode> fringe, SearchNode goal) {
        while (!fringe.isEmpty()) {
            SearchNode curr = fringe.delMin();
            if (curr == goal) return;
            for (WorldState w : curr.neighbors()) {
                SearchNode nb = new SearchNode(w, curr.NumberOfMoves + 1, curr);
                fringe.insert(nb);
            }
        }
    }
}
