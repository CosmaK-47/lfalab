package org.example;
import java.util.Set; // <-- Add this line


public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("S");
        grammar.terminals.addAll(Set.of("a", "b", "d"));

        grammar.addProduction("S", "dB");
        grammar.addProduction("S", "A");
        grammar.addProduction("A", "d");
        grammar.addProduction("A", "dS");
        grammar.addProduction("A", "aBdB");
        grammar.addProduction("B", "a");
        grammar.addProduction("B", "aS");
        grammar.addProduction("B", "AC");
        grammar.addProduction("D", "AB");
        grammar.addProduction("C", "bC");
        grammar.addProduction("C", "ε");

        System.out.println("Original Grammar:");
        grammar.printGrammar();

        CNFConverter.normalize(grammar);

        System.out.println("\nCNF Grammar:");
        grammar.printGrammar();
    }
}