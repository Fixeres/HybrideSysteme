package com.company;

import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import static com.company.TruthValueConstraint.*;

/**
 * Created by Alex on 08.08.2019.
 */
public class AlgorithmC extends AlgorithmB {

    private int positionX;
    private int positionY;

    private ArrayList<TruthValueConstraint> truthValSC;

    @Override
    public void start(CSP csp) {
        this.csp = csp;
        truthValSC = new ArrayList<TruthValueConstraint>();
        for(int i = 0; i < csp.getSimpleConstraints().size(); i++){
            truthValSC.add(NOT_VISITED);
        }
        positionX = -1;
        positionY = -1;
        doAlgorithmA1();
    }

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

        if(!needToCheckConstraint()){
            if(csp.getSimpleConstraints().size() - 1 == cIndex){
                if(truthValSC.get(cIndex).equals(TRUE)){
                    System.out.println("P is satisfiable");
                    System.out.println(System.nanoTime()-startTime);
                    return;
                }else if(truthValSC.get(cIndex).equals(FALSE)){
                    bIndex = 0;
                    cIndex = 0;
                    doAlgorithmA2();
                }else if(truthValSC.get(cIndex).equals(INCONCLUSIVE)){
                    bIndex = 0;
                    cIndex = 0;
                    doAlgorithmA3();
                }else{
                    throw new RuntimeException();
                }
            } else {
                cIndex++;
                bIndex = 0;
                doAlgorithmA1();
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
                        doDeduction();
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
                unitSB = csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(bIndex);
            }else {
                unit=false;
            }
            isInconclusive = true;

            if (csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size() - 1 == bIndex) {
                cIndex = 0;
                bIndex = 0;
                if(unit){
                    doDeduction();
                }else {
                    doAlgorithmA3();
                }
            } else {
                bIndex++;
                doAlgorithmA1();
            }
        }
    }

    @Override
    protected void doDeduction() {

        positionX = -1;
        positionY = -1;

        boolean narrowed= false;

        unit=false;
        isInconclusive=false;

        int newXL;
        int newYU;

        Variable x = null;
        Variable y = null;

        for(Variable var : csp.getVars()){
            if(var.getPosition() == unitSB.getX().getPosition()){
                x = var;
            } else if(var.getPosition() == unitSB.getY().getPosition()){
                y = var;
            }
        }

        int xU = x.getUpperDomainBound();
        int yL = y.getLowerDomainBound();

        newXL = yL + unitSB.getCright();
        newYU = xU - unitSB.getCright();

        if(newXL>x.getLowerDomainBound()){
            Variable newX = new Variable(newXL, xU);
            newX.setPosition(x.getPosition());
            positionX = newX.getPosition();
            changeVariable(newX);
            narrowed= true;
        }
        if (newYU< y.getUpperDomainBound()){
            Variable newY = new Variable(yL, newYU);
            newY.setPosition(y.getPosition());
            positionY = newY.getPosition();
            changeVariable(newY);
            narrowed =true;
        }

        if(narrowed){
            doAlgorithmA1();
        }else {
            doAlgorithmA3();
        }

    }

    @Override
    protected void doAlgorithmA2() {
        if (stack.empty()) {
            System.out.println("P is unsatisfiable");
            System.out.println(System.nanoTime() - startTime);
            return;
        } else {

            Variable variable = stack.pop();

            resetTruthList();
            changeVariable(variable);

            doAlgorithmA1();
        }
    }

    @Override
    protected void doAlgorithmA3() {

        positionY = -1;
        isInconclusive = false;

        Variable splitVariable1 = null;
        Variable splitVariable2 = null;
        for (Variable variable : csp.getVars()) {
            if (variable.getUpperDomainBound() != variable.getLowerDomainBound()) {
                splitVariable1 = new Variable(variable.getLowerDomainBound(),
                        (variable.getLowerDomainBound() + ((variable.getUpperDomainBound() - variable.getLowerDomainBound()) / 2)));
                splitVariable1.setPosition(variable.getPosition());
                splitVariable2 = new Variable(splitVariable1.getUpperDomainBound() + 1, variable.getUpperDomainBound());
                splitVariable2.setPosition(variable.getPosition());
                break;
            }
        }

        if (splitVariable1 != null) {
            changeVariable(splitVariable1);
            positionX = splitVariable1.getPosition();
            stack.push(splitVariable2);
            doAlgorithmA1();
        } else {
            doAlgorithmA2();
        }
    }

    private boolean needToCheckConstraint(){
        if(truthValSC.get(cIndex).equals(TRUE)){
            return false;
        }
        for(int i = 0; i < csp.getSimpleConstraints().get(cIndex).getSimpleBounds().size(); i++){
            if(csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(i).getX() != null &&
                    csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(i).getX().getPosition() == positionX){
                return true;
            } else if(csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(i).getY() != null &&
                    csp.getSimpleConstraints().get(cIndex).getSimpleBounds().get(i).getY().getPosition() == positionY){
                return true;
            }
        }
        if(truthValSC.get(cIndex).equals(NOT_VISITED)){
            return true;
        }
        return false;
    }

    private void resetTruthList(){
        for(int i = 0; i < truthValSC.size(); i++){
            truthValSC.set(i, NOT_VISITED);
        }
        positionX = -1;
        positionY = -1;
    }

}
