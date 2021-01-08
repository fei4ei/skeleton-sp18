import java.util.Comparator;

class Node {
    final long id;
    final double lat;
    final double lon;
//    double distTo;
//    double heuristic;
//    long edgeTo;
    Node(long ID, double Lat, double Lon) {
        id = ID;
        lat = Lat;
        lon = Lon;
//        distTo = Double.POSITIVE_INFINITY;
//        heuristic = 0.0;
//        edgeTo = -1;
    }

//    public void updateHeuristic(double h) {
//        this.heuristic = h;
//    }
//
//    public void updatedistTo(double dist) {
//        this.distTo = dist;
//    }

//    private static class distComparator implements Comparator<Node> {
//
//        @Override
//        public int compare(Node o1, Node o2) {
//            double result = o1.distTo + o1.heuristic - o2.distTo - o2.heuristic;
//            if (result < 0.0) {
//                return -1;
//            } else if (result > 0.0) {
//                return 1;
//            } else {
//                return 0;
//            }
//        }
//    }

}