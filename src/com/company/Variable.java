package com.company;

/**
 * Data structure for the variables
 * saves domain and position
 */
class Variable {
    private int lowerDomainBound;
    private int upperDomainBound;
    private int position;

    Variable(int lowerDomainBound, int upperDomainBound) {
        this.lowerDomainBound = lowerDomainBound;
        this.upperDomainBound = upperDomainBound;
    }


    void setPosition(int position){
        this.position = position;
    }


    int getLowerDomainBound() {
        return lowerDomainBound;
    }

    int getUpperDomainBound() {
        return upperDomainBound;
    }

    int getPosition(){
        return this.position;
    }
}
