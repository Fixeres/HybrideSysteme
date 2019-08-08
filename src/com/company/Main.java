package com.company;

public class Main {
    public static void main(String[] args) {
        String file = "src/resources/Example7.txt";
        CSP csp = new Parser().createCSP(file);
        csp.printCSP();
        IAlgorithm algorithm = new AlgorithmC();
        algorithm.start(csp);
        csp.printCSP();
    }
}