package com.company;

/**
 * This class represents the Simple Bounds
 * the variables are put here
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
