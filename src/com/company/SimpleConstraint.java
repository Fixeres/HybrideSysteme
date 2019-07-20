package com.company;

import java.util.ArrayList;

/**
 * Created by Alex on 20.07.2019.
 */
public class SimpleConstraint {

    private ArrayList<SimpleBound> simpleBounds = new ArrayList<SimpleBound>();

    public SimpleConstraint(){}

    public void addSimpleBound(SimpleBound sB){
        simpleBounds.add(sB);
    }

    public ArrayList<SimpleBound> getSimpleBounds() {
        return simpleBounds;
    }
}
