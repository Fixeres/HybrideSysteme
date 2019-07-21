package com.company;

public class Variable {
    private int X;
    private int lowerDomainBound;
    private int upperDomainBound;
    private int position;

    public Variable(int lowerDomainBound, int upperDomainBound) {
        this.lowerDomainBound = lowerDomainBound;
        this.upperDomainBound = upperDomainBound;
    }

    public void setX(int x){
        this.X =x;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getX() {
        return X;
    }

    public int getLowerDomainBound() {
        return lowerDomainBound;
    }

    public int getUpperDomainBound() {
        return upperDomainBound;
    }

    public int getPosition(){
        return this.position;
    }
}
