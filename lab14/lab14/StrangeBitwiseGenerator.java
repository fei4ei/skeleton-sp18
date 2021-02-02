package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public double next() {
        state = (state + 1);
        int weirdState = state & (state >>> 3);
        return normalize(weirdState % period);
    }

    private double normalize(int x) {
        return 2.0*x/(period-1) - 1;
    }
}
