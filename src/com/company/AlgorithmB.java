package com.company;

public class AlgorithmB extends AlgorithmA {

    boolean unit= false;

    @Override
    protected void doAlgorithmA1() {
        int xP = 0;
        int yP = 0;

        if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getX() != null) {
            xP = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getX().getPosition();
        }
        int xU = 0;
        int xL = 0;
        if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getY() != null) {
            yP = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getY().getPosition();
        }
        int yU = 0;
        int yL = 0;
        int cright = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getCright();
        int cleft = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getCleft();


        for (Variable variable : csp.getVars()) {
            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getX() != null) {
                if (variable.getPosition() == xP) {
                    xU = variable.getUpperDomainBound();
                    xL = variable.getLowerDomainBound();
                }
            }
            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex).getY() != null) {
                if (variable.getPosition() == yP) {
                    yU = variable.getUpperDomainBound();
                    yL = variable.getLowerDomainBound();
                }
            }
        }

        boolean first = false;
        boolean second = false;


        if (xL + cleft >= yU + cright) {
            first = true;
        }
        if (xU + cleft < yL + cright) {
            second = true;
        }

        if (first != second) {
            if (first) {    //a true Simple Bound was found
                if (csp.getSimpleConstraints().size() - 1 == cIndex) {
                    System.out.println("P is satisfiable");
                    System.out.println(System.nanoTime()-startTime);
                    return;
                } else {
                    bIndex = 0;
                    cIndex++;
                    unit=false;
                    doAlgorithmA1();
                }
            } else if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size() - 1 == bIndex) {  //a false Simple Bound was found
                bIndex = 0;
                cIndex = 0;
                if (isInconclusive) {
                    if(unit){
                        doDeduction(xP,xL,xU,yP,yL,yU,cright);
                    }else {
                        doAlgorithmA3();
                    }
                } else {
                    doAlgorithmA2();
                }
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        } else {//an inconclusive Simple Bound was found
            if(!isInconclusive){
                unit=true;
            }else {
                unit=false;
            }
            isInconclusive = true;

            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size() - 1 == bIndex) {
                cIndex = 0;
                bIndex = 0;
                if(unit){
                    doDeduction(xP,xL,xU,yP,yL,yU,cright);
                }else {
                    doAlgorithmA3();
                }
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        }
    }

    private void doDeduction(int xP,int xL,int xU, int yP, int yL,int yU,int cright) {

        boolean narrowed= false;

        unit=false;
        isInconclusive=false;

        int newXL;
        int newYU;

        newXL = yL + cright;
        newYU = xU - cright;

        if(newXL>xL){
            Variable newX = new Variable(newXL, xU);
            newX.setPosition(xP);
            changeVariable(newX);
            narrowed= true;
        }
        if (newYU< yU){
            Variable newY = new Variable(yL, newYU);
            newY.setPosition(yP);
            changeVariable(newY);
            narrowed =true;
        }

        if(narrowed){
            doAlgorithmA1();
        }else {
            doAlgorithmA3();
        }

    }


}

