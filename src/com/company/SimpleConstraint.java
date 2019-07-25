package com.company;

import java.util.ArrayList;

/**
 * Created by Alex on 20.07.2019.
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
