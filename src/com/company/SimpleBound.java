package com.company;

/**
 * Created by Alex on 20.07.2019.
 */
class SimpleBound {
    private Variable x;
    private Variable y;
    private int cleft;
    private int cright;

    SimpleBound(Variable x, Variable y, int cleft, int cright) {
        this.x = x;
        this.y = y;
        this.cleft = cleft;
        this.cright = cright;
    }

    Variable getX() {
        return x;
    }

    Variable getY() {
        return y;
    }

    int getCleft() {
        return cleft;
    }

    int getCright() {
        return cright;
    }
}
