package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

public class SearchNode implements WorldState{
    WorldState ws;
    int NumberOfMoves; // the number of moves made to reach this world state from the initial state.
    SearchNode Prev; // reference to the previous node
    int edtg = 0; // caching

    public SearchNode(WorldState input, int nom, SearchNode previous) {
        ws = input;
        NumberOfMoves = nom;
        Prev = previous;
        edtg = estimatedDistanceToGoal();
    }

    public SearchNode(WorldState input) {
        this(input, 0, null);
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o, SearchNode p) {
//            return (o.NumberOfMoves + o.ws.estimatedDistanceToGoal()) -
//                    (p.NumberOfMoves + p.ws.estimatedDistanceToGoal());
            return (o.NumberOfMoves + o.edtg) - (p.NumberOfMoves + p.edtg);
        }
    }

    public static Comparator<SearchNode> SNComparator() {
        return new SearchNodeComparator();
    }

    @Override
    public int estimatedDistanceToGoal() {
        return ws.estimatedDistanceToGoal();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        return ws.neighbors();
    }

    public static MinPQ<SearchNode> createPQ(WorldState inputstate) {
        MinPQ<SearchNode> fringe = new MinPQ<>(SNComparator()); // SNComparator supplies the order in which to compare the keys
        SearchNode inputnode = new SearchNode(inputstate);
        fringe.insert(inputnode);
        return fringe;
    }

    public boolean wsEquals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchNode onode = (SearchNode) o;
        return (this.ws.equals(onode.ws));
    }

}
