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

        //Taking the variable out of the list that are in the bounds
        //Testing that the bound has variables
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

        //Testing if the bound is true, false or inconclusive
        if (xL + cleft >= yU + cright) {
            first = true;
        }
        if (xU + cleft < yL + cright) {
            second = true;
        }

        //are first and second not equal is the bound not inconclusive
        if (first != second) {
            if (first) {   //a true Simple Bound was found
                //checks if it was the last constraint in a csp
                //If so the csp is true
                //else check the next constraint and do this method again
                if (csp.getSimpleConstraints().size() - 1 == cIndex) {
                    System.out.println("P is satisfiable");
                    System.out.println(System.nanoTime() - startTime);
                    return;
                } else {
                    bIndex = 0;
                    cIndex++;
                    doAlgorithmA1();
                }
            } else if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size() - 1 == bIndex) {  //a false Simple Bound was found and is the last bound in the constraint
                bIndex = 0;
                cIndex = 0;

                //if an inconclusive bound is in the constraint do Step 3
                //if not do Step 2
                if (isInconclusive) {
                    doAlgorithmA3();
                } else {
                    doAlgorithmA2();
                }
            }
            //if it is not the last bound check the next, the constraint could have a true value
            else {
                bIndex++;
                doAlgorithmA1();
            }
        } else {//an inconclusive Simple Bound was found
            //set the value on true and then check the next bounds if it wasn't the last
            //else do Step 3
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


    /**
     * Implementation of Step 2
     * If the stack is empty than the csp unsatisfiable
     * else change the variable with the last variable of the stack
     */
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

    /**
     * Implementation of Step 3
     * Sets values back
     * Splits a variable and chooses one and puts the ohter in the stack
     * The algorithm is than done all over again
     */
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

    /**
     * Changes the variable with the one out of the stack
     * @param variable
     */
    protected void changeVariable(Variable variable) {
        for (Variable variable1 : csp.getVars()) {
            if (variable.getPosition() == variable1.getPosition()) {
                csp.getVars().set(csp.getVars().indexOf(variable1), variable);
                break;
            }
        }
    }
}
