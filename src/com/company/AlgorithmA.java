package com.company;

import java.util.Stack;

public class AlgorithmA implements IAlgorithm {

    protected CSP csp;

    protected int cIndex = 0;
    protected int bIndex = 0;
    protected boolean isInconclusive = false;

    protected Stack<Variable> stack = new Stack();
    long startTime = System.nanoTime();

    @Override
    public void start(CSP csp) {
        this.csp = csp;
        doAlgorithmA1();
    }

    protected void doAlgorithmA1() {

        int xP = 0;
        int yP = 0;

        if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getX() != null) {
            xP = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getX().getPosition();
        }
        int xU = 0;
        int xL = 0;
        if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getY() != null) {
            yP = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getY().getPosition();
        }
        int yU = 0;
        int yL = 0;
        int cright = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getCright();
        int cleft = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getCleft();


        for (Variable variable : csp.getVars()) {
            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getX() != null) {
                if (variable.getPosition() == xP) {
                    xU = variable.getUpperDomainBound();
                    xL = variable.getLowerDomainBound();
                }
            }
            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getY() != null) {
                if (variable.getPosition() == yP) {
                    yU = variable.getUpperDomainBound();
                    yL = variable.getLowerDomainBound();
                }
            }
        }

        boolean first = false;
        boolean second = false;


        if (xL + cleft >= yU + cright) {
            first = true;
        }
        if (xU + cleft < yL + cright) {
            second = true;
        }

        if (first != second) {
            if (first) {    //a true Simple Bound was found
                if (csp.getSimpleConstraints().size() - 1 == cIndex) {
                    System.out.println("P is satisfiable");
                    System.out.println(System.nanoTime() - startTime);
                    return;
                } else {
                    bIndex = 0;
                    cIndex++;
                    doAlgorithmA1();
                }
            } else if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size() - 1 == bIndex) {  //a false Simple Bound was found
                bIndex = 0;
                cIndex = 0;
                if (isInconclusive) {
                    doAlgorithmA3();
                } else {
                    doAlgorithmA2();
                }
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        } else {//an inconclusive Simple Bound was found
            isInconclusive = true;
            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size() - 1 == bIndex) {
                cIndex = 0;
                bIndex = 0;
                doAlgorithmA3();
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        }
    }


    protected void doAlgorithmA2() {
        if (stack.empty()) {
            System.out.println("P is unsatisfiable");
            System.out.println(System.nanoTime() - startTime);
            return;
        } else {

            Variable variable = stack.pop();

            changeVariable(variable);

            doAlgorithmA1();
        }
    }

    protected void doAlgorithmA3() {

        isInconclusive = false;

        Variable splitVariable1 = null;
        Variable splitVariable2 = null;
        for (Variable variable : csp.getVars()) {
            if (variable.getUpperDomainBound() != variable.getLowerDomainBound()) {
                splitVariable1 = new Variable(variable.getLowerDomainBound(),
                        (variable.getLowerDomainBound() + ((variable.getUpperDomainBound() - variable.getLowerDomainBound()) / 2)));
                splitVariable1.setPosition(variable.getPosition());
                splitVariable2 = new Variable(splitVariable1.getUpperDomainBound() + 1, variable.getUpperDomainBound());
                splitVariable2.setPosition(variable.getPosition());
                break;
            }
        }

        if (splitVariable1 != null) {
            changeVariable(splitVariable1);
            stack.push(splitVariable2);
            doAlgorithmA1();
        } else {
            doAlgorithmA2();
        }
    }

    protected void changeVariable(Variable variable) {
        for (Variable variable1 : csp.getVars()) {
            if (variable.getPosition() == variable1.getPosition()) {
                csp.getVars().set(csp.getVars().indexOf(variable1), variable);
                break;
            }
        }
    }
}
