package com.company;

import java.util.ArrayList;

/**
 * Created by Alex on 20.07.2019.
 */
public class CSP {
    private ArrayList<SimpleConstraint> simpleConstraints;
    private ArrayList<Variable> vars;

    public CSP(){
        simpleConstraints = new ArrayList<SimpleConstraint>();
        vars = new ArrayList<Variable>();
    }

    public void addSimpleConstraint(SimpleConstraint sC){
        this.simpleConstraints.add(sC);
    }

    public void addVariable(Variable var){
        this.vars.add(var);
    }
    public ArrayList<SimpleConstraint> getSimpleConsraints() {
        return simpleConstraints;
    }

    public ArrayList<Variable> getVars() {
        return vars;
    }

    public void printCSP(){
        for(int i = 0; i < simpleConstraints.size(); i++){
            for(int j = 0; j < simpleConstraints.get(i).getSimpleBounds().size(); j++){
                SimpleBound sB = simpleConstraints.get(i).getSimpleBounds().get(j);
                if(sB.getX() == null) {
                    System.out.print(sB.getCleft() + " >= " + sB.getCright());
                } else {
                    System.out.print("x_" + sB.getX().getPosition() +
                            " >= x_" + sB.getY().getPosition() +
                            " + " + sB.getCleft());
                }
                if(j < simpleConstraints.get(i).getSimpleBounds().size() - 1){
                    System.out.print(" v ");
                }
            }
            System.out.println(";");
        }
    }
}
