package com.company;

public class Main {
    public static void main(String[] args) {
        String file = "/resources/CSP.txt";
        CSP csp = new Parser().createCSP(file);
        csp.printCSP();
    }
}