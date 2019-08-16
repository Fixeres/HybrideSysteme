package com.company;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Data structure of csp
 */

class CSP {

    private ArrayList<SimpleConstraint> simpleConstraints;
    private ArrayList<Variable> vars;

    CSP() {
        simpleConstraints = new ArrayList<>();
        vars = new ArrayList<>();
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

    public ArrayList<SimpleConstraint> getSimpleConstraints() { return simpleConstraints; }


}
