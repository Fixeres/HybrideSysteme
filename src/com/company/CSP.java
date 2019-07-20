package com.company;

import java.util.ArrayList;

/**
 * Created by Alex on 20.07.2019.
 */
public class CSP {
    private ArrayList<SimpleConstraint> simpleConsraints = new ArrayList<SimpleConstraint>();

    public CSP(){}

    public void addSimpleConstraint(SimpleConstraint sC){
        simpleConsraints.add(sC);
    }

    public ArrayList<SimpleConstraint> getSimpleConsraints() {
        return simpleConsraints;
    }
}
