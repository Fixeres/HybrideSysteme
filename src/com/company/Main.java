package com.company;

public class Main {
    public static void main(String[] args) {
        String file = "src/resources/Example7.txt";
        CSP csp = new Parser().createCSP(file);
        IAlgorithm algorithm = new AlgorithmA();
        algorithm.start(csp);
        System.out.println("");
        csp.printCSP();
    }
}