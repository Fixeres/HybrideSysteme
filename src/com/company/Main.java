package com.company;

public class Main {
    public static void main(String[] args) {
        String file = "src/resources/test.txt";
        CSP csp = new Parser().createCSP(file);
        csp.printCSP();
        IAlgorithm algorithm = new AlgorithmB();
        algorithm.start(csp);
        csp.printCSP();
    }
}