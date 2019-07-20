package com.company;

public class Main {

    static final int c = 1;

    public static void main(String[] args) {
        int x = 3;
        int y = 4;
        SimpleBound sB = new SimpleBound(x, y, c);
        SimpleConstraint sC = new SimpleConstraint();
        sC.addSimpleBound(sB);
        CSP P = new CSP();
        P.addSimpleConstraint(sC);
        System.out.printf("X = " + P.getSimpleConsraints().get(0).getSimpleBounds().get(0).getX());
    }
}
