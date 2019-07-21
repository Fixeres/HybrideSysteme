package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Parser {

    CSP csp;

    public Parser() {
        csp = new CSP();
    }

    public CSP createCSP(String file) {
        List<String> lines = readFile(file);
        if (lines == null) {
            return null;
        }
        //Initalize Variables
        int lineNumber;
        boolean initVars = false;
        boolean initSbs = false;
        for (lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
            String line = lines.get(lineNumber);
            if (line.equals("")) {
                continue;
            }
            if (line.equals("DECL")) {
                initVars = true;
                continue;
            }
            if (line.equals("FORMULA")) {
                initVars = false;
                initSbs = true;
                continue;
            }
            if (initVars) {
                initVariables(line);
            } else if (initSbs) {
                initSbs(line);
            }
        }
        return this.csp;
    }

    private List<String> readFile(String file) {
        try {
            return Files.readAllLines(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initVariables(String line) {
        String[] parts = line.split(" ");

        int counter = 0;
        int lb = 0;
        int rb = 0;
        int position = 0;
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            part = part.replace("x", "");
            part = part.replace(";", "");
            part = part.replace("\t", "");
            part = part.replace("_", "");
            if (i == 0) {
                position = Integer.parseInt(part);
            } else if (i == 1) {
                lb = Integer.parseInt(part);
            } else if (i == 2) {
                rb = Integer.parseInt(part);
            }
        }
        Variable var = new Variable(lb, rb);
        var.setPosition(position);
        csp.addVariable(var);
        if (csp.getVars().indexOf(var) != position) {
            throw new RuntimeException();
        }
    }

    private void initSbs(String line) {
        SimpleConstraint sC = new SimpleConstraint();
        String[] sBs = line.split("v");

        for (int i = 0; i < sBs.length; i++) {
            String sB = sBs[i];
            String[] parts = sB.split(">=");
            int indexVar1 = 0;
            int indexVar2 = 0;
            int cleft = 0;
            int cright = 0;
            for (int j = 0; j < parts.length; j++) {
                String part = parts[j];
                part = part.replace("\t", "");
                part = part.replace("_", "");
                part = part.replace(" ", "");
                part = part.replace(";", "");
                if (part.indexOf("x") == -1) {
                    if(j == 0){
                        indexVar1 = -1;
                        cleft = Integer.parseInt(part);
                    } else if (j == 1){
                        indexVar2 = -1;
                        cright = Integer.parseInt(part);
                    }
                } else {
                    part = part.replace("x", "");
                    if (j == 0) {
                        indexVar1 = Integer.parseInt(part);
                    } else if (j == 1) {
                        String[] values = part.split("\\+");
                        indexVar2 = Integer.parseInt(values[0]);
                        cright = Integer.parseInt(values[1]);
                    }
                }
            }
            SimpleBound simpleBound;
            if(indexVar1 == -1 && indexVar2 == -1){
                simpleBound = new SimpleBound(null, null, cleft, cright);
            } else {
                simpleBound = new SimpleBound(csp.getVars().get(indexVar1), csp.getVars().get(indexVar2), cleft, cright);
            }
            sC.addSimpleBound(simpleBound);
        }
        csp.addSimpleConstraint(sC);
    }
}
