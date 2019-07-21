package com.company;

/**
 * Created by Alex on 20.07.2019.
 */
public class SimpleBound {
    private Variable x;
    private Variable y;
    private int cleft;
    private int cright;

    public SimpleBound(Variable x, Variable y, int cleft, int cright) {
        this.x = x;
        this.y = y;
        this.cleft = cleft;
        this.cright = cright;
    }

    public Variable getX() {
        return x;
    }

    public Variable getY() {
        return y;
    }

    public int getCleft() {
        return cleft;
    }

    public int getCright() {
        return cright;
    }
}
