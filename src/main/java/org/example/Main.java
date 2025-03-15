package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Map<String, List<String>> productionRules = new HashMap<>();
        productionRules.put("S", Arrays.asList("bA"));
        productionRules.put("A", Arrays.asList("b", "aB", "bA"));
        productionRules.put("B", Arrays.asList("bC", "aB"));
        productionRules.put("C", Arrays.asList("cA"));

        Grammar grammar = new Grammar(alphabet, productionRules, "S");
        System.out.println("Grammar Classification: " + grammar.classifyGrammar());

        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>();
        transitions.put("q0", Map.of('a', Set.of("q0", "q1")));
        transitions.put("q1", Map.of('b', Set.of("q1"), 'a', Set.of("q2")));
        transitions.put("q2", Map.of('b', Set.of("q2"), 'a', Set.of("q0")));
        Set<String> finalStates = Set.of("q2");

        FiniteAutomaton fa = new FiniteAutomaton(transitions, finalStates);
        System.out.println("Is Deterministic: " + fa.isDeterministic());

        fa.printTransitions("NDFA Transitions:");

        FiniteAutomaton dfa = fa.convertToDFA();
        dfa.printTransitions("DFA Transitions:");
        System.out.println("NDFA converted to DFA.");

        System.out.println("Regular Grammar:");
        fa.toRegularGrammar().forEach((k, v) -> System.out.println(k + " -> " + v));

        try {
            fa.generateDOTFile("fa.dot");

            Process process = Runtime.getRuntime().exec("dot -Tpng fa.dot -o fa.png");
            process.waitFor();

            System.out.println("PNG generated successfully.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
