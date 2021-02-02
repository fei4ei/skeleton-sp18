package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double acfactor;
    private int cycle;

    public AcceleratingSawToothGenerator(int period, double acfactor) {
        this.state = 0;
        this.period = period;
        this.cycle = period;
        this.acfactor = acfactor;
    }

    public double next() {
        state = state + 1;
        return normalize(state % period);
    }

    private double normalize(int x) {
        double y = 2.0*x/(period-1) - 1;
        if (x==0) {
            this.period = (int) (this.period * acfactor);
            state = (int) (state*acfactor);
        }
        return y;
    }
}
