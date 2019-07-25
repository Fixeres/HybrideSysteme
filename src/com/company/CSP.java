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
    private boolean isInconclusive = false;

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

        boolean first = false;
        boolean second = false;

        if (xL >= yU + cright) {
            first = true;
        }
        if (xU < yL + cright) {
            second = true;
        }

        if (first != second) {
            if (first) {    //a true Simple Bound was found
                if (simpleConstraints.size() - 1 == cIndex) {
                    System.out.println("P is satisfiable");
                    return;
                } else {
                    bIndex = 0;
                    cIndex++;
                    doAlgorithmA1();
                }
            } else if (simpleConstraints.get(cIndex).getSimpleBounds().size() - 1 == bIndex) {  //a false Simple Bound was found
                bIndex = 0;
                cIndex = 0;
                if(isInconclusive){
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
            if(simpleConstraints.get(cIndex).getSimpleBounds().size() - 1 == bIndex) {
                cIndex = 0;
                bIndex = 0;
                doAlgorithmA3();
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        }
    }


    private void doAlgorithmA2() {
        if (stack.empty()) {
            System.out.println("P is unsatisfiable");
            return;
        } else {

            Variable variable = stack.pop();

            changeVariable(variable);

            doAlgorithmA1();
        }
    }

    private void doAlgorithmA3() {

        isInconclusive = false;

        Variable splitVariable1 = null;
        Variable splitVariable2 = null;
        for(Variable variable: vars){
            if(variable.getUpperDomainBound() != variable.getLowerDomainBound()){
                splitVariable1 = new Variable(variable.getLowerDomainBound(),
                        (variable.getLowerDomainBound() + ((variable.getUpperDomainBound() - variable.getLowerDomainBound())/ 2)));
                splitVariable1.setPosition(variable.getPosition());
                splitVariable2 = new Variable(splitVariable1.getUpperDomainBound() + 1, variable.getUpperDomainBound());
                splitVariable2.setPosition(variable.getPosition());
                break;
            }
        }

        if(splitVariable1 != null) {
            changeVariable(splitVariable1);
            stack.push(splitVariable2);
            doAlgorithmA1();
        } else {
            doAlgorithmA2();
        }
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
