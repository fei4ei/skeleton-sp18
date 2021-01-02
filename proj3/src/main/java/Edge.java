class Edge {
    long id;
    final int v;
    final int w;
    boolean flag;
    Edge(long ID, int V, int W) {
        id = ID;
        flag = false;
        v = V;
        w = W;
    }

    public int one() {
        return v;
    }

    public int other() {
        return w;
    }

}