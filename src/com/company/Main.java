package com.company;

public class Main {
    public static void main(String[] args) {
        String file = "src/resources/CSP.txt";
        CSP csp = new Parser().createCSP(file);
        csp.printCSP();
        csp.doAlgorithmA1();
    }
}