package com.company;

import java.util.ArrayList;

/**
 * Data structure for the Simple Constraints
 */
class SimpleConstraint {

    private ArrayList<SimpleBound> simpleBounds = new ArrayList<SimpleBound>();

    SimpleConstraint(){}

    void addSimpleBound(SimpleBound sB){
        simpleBounds.add(sB);
    }

    ArrayList<SimpleBound> getSimpleBounds() {
        return simpleBounds;
    }
}
