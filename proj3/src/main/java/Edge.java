class Edge {
    long id;
    final long v;
    final long w;
    double maxspeed;
    Edge(long ID, long V, long W) {
        id = ID;
        v = V;
        w = W;
        maxspeed = -1;
    }

    long getID() {
        return this.id;
    }

    void setMaxspeed(double Maxspeed) {
        maxspeed = Maxspeed;
    }

    long either() {
        return v;
    }

    long other(long vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent Edge");
    }

}