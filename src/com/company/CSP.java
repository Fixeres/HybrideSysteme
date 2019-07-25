package com.company;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Alex on 20.07.2019.
 */

class CSP {
    private ArrayList<SimpleConstraint> simpleConstraints;
    private ArrayList<Variable> vars;

    private int cIndex = 0;
    private int bIndex = 0;

    private Stack<Variable> stack = new Stack();

    CSP() {
        simpleConstraints = new ArrayList<>();
        vars = new ArrayList<>();
    }


    void doAlgorithmA1() {

        int xP = simpleConstraints.get(cIndex).getSimpleBounds().get(bIndex).getX().getPosition();
        int xU = 0;
        int xL = 0;
        int yP = simpleConstraints.get(cIndex).getSimpleBounds().get(bIndex).getY().getPosition();
        int yU = 0;
        int yL = 0;
        int cright = simpleConstraints.get(cIndex).getSimpleBounds().get(bIndex).getCright();


        for (Variable variable : vars) {
            if (variable.getPosition() == xP) {
                xU = variable.getUpperDomainBound();
                xL = variable.getLowerDomainBound();
            }
            if (variable.getPosition() == yP) {
                yU = variable.getUpperDomainBound();
                yL = variable.getLowerDomainBound();
            }
        }

        /*
        System.out.println(xP);
        System.out.println(xU);
        System.out.println(xL);
        System.out.println(yP);
        System.out.println(yU);
        System.out.println(yL);
        System.out.println(cright);
        */

        boolean first = false;
        boolean second = false;

        if (xL >= yU + cright) {
            first = true;
        }
        if (xU < yL + cright) {
            second = true;
        }

        if (first == second) {
            if (first) {
                if (simpleConstraints.size() - 1 == cIndex) {
                    System.out.print("P is satisfiable");
                } else {
                    bIndex = 0;
                    cIndex++;
                    doAlgorithmA1();
                }
            } else if (simpleConstraints.get(cIndex).getSimpleBounds().size() - 1 == bIndex) {
                bIndex = 0;
                cIndex = 0;
                doAlgorithmA2();
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        } else {
            cIndex = 0;
            bIndex = 0;
            doAlgorithmA3();
        }
    }


    private void doAlgorithmA2() {
        if (stack.empty()) {
            System.out.print("P is unsatisfiable");
        } else {

            Variable variable = stack.pop();

            changeVariable(variable);

            doAlgorithmA1();
        }
    }

    private void doAlgorithmA3() {

        int xOld = 0;
        int yNew = 0;
        int xNew = 0;
        int yOld = 0;

        int position = 0;



        Variable variable1 = new Variable(0, 0);
        variable1.setPosition(position);
        changeVariable(variable1);

        Variable variable2 = new Variable(0, 0);
        variable2.setPosition(position);
        stack.push(variable2);

        doAlgorithmA1();
    }

    private void changeVariable(Variable variable) {
        for (Variable variable1 : vars) {
            if (variable.getPosition() == variable1.getPosition()) {
                vars.set(vars.indexOf(variable1), variable);
                break;
            }
        }
    }


    void printCSP() {
        System.out.println("DECL");
        printDecl();
        System.out.println();
        System.out.println("FORMULA");
        printFormula();
    }

    private void printDecl() {
        for (Variable var : vars) {
            System.out.println("x_" + var.getPosition() +
                    " " + var.getLowerDomainBound() +
                    " " + var.getUpperDomainBound() + ";");
        }
    }

    private void printFormula() {
        for (SimpleConstraint simpleConstraint : simpleConstraints) {
            for (int j = 0; j < simpleConstraint.getSimpleBounds().size(); j++) {
                SimpleBound sB = simpleConstraint.getSimpleBounds().get(j);
                if (sB.getX() == null) {
                    System.out.print(sB.getCleft() + " >= " + sB.getCright());
                } else {
                    System.out.print("x_" + sB.getX().getPosition() +
                            " >= x_" + sB.getY().getPosition() +
                            " + " + sB.getCright());
                }
                if (j < simpleConstraint.getSimpleBounds().size() - 1) {
                    System.out.print(" v ");
                }
            }
            System.out.println(";");
        }
    }


    void addSimpleConstraint(SimpleConstraint sC) {
        this.simpleConstraints.add(sC);
    }

    void addVariable(Variable var) {
        this.vars.add(var);
    }

    ArrayList<Variable> getVars() {
        return vars;
    }
}
