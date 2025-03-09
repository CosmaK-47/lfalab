package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Define the Finite Automaton (FA)
        Set<String> states = new HashSet<>(Arrays.asList("S", "A", "B", "C"));
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c'));
        String startState = "S";
        Set<String> finalStates = new HashSet<>(Collections.singletonList("A"));

        // Transition function (NDFA)
        Map<String, Map<Character, Set<String>>> transitionFunction = new HashMap<>();
        transitionFunction.put("S", new HashMap<>() {{
            put('b', new HashSet<>(Collections.singletonList("A")));
        }});
        transitionFunction.put("A", new HashMap<>() {{
            put('b', new HashSet<>(Collections.singletonList("A"))); // A -> bA
            put('a', new HashSet<>(Collections.singletonList("B"))); // A -> aB
        }});
        transitionFunction.put("B", new HashMap<>() {{
            put('b', new HashSet<>(Collections.singletonList("C"))); // B -> bC
            put('a', new HashSet<>(Collections.singletonList("B"))); // B -> aB
        }});
        transitionFunction.put("C", new HashMap<>() {{
            put('c', new HashSet<>(Collections.singletonList("A"))); // C -> cA
        }});

        // Create the FA
        FiniteAutomaton fa = new FiniteAutomaton(states, alphabet, transitionFunction, startState, finalStates);

        // Check if the FA is deterministic or non-deterministic
        System.out.println("Is the FA deterministic? " + fa.isDeterministic());

        // Convert the FA to a Regular Grammar
        Grammar regularGrammar = fa.convertToRegularGrammar();
        System.out.println("Grammar classification: " + regularGrammar.classifyGrammar());

        // Print the productions of the grammar
        System.out.println("Productions:");
        Map<String, List<String>> productions = regularGrammar.getProductions();
        for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
            String lhs = entry.getKey();
            List<String> rhsList = entry.getValue();

            if (rhsList == null || rhsList.isEmpty()) {
                System.out.println(lhs + " -> ε");
                continue;
            }

            System.out.print(lhs + " -> ");
            for (int i = 0; i < rhsList.size(); i++) {
                System.out.print(rhsList.get(i));
                if (i < rhsList.size() - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }

        // Convert NDFA to DFA
        DFA dfa = fa.convertNDFAtoDFA();

        // Print DFA details
        System.out.println("\nDFA States: " + dfa.getStates());
        System.out.println("DFA Alphabet: " + dfa.getAlphabet());
        System.out.println("DFA Start State: " + dfa.getStartState());
        System.out.println("DFA Final States: " + dfa.getFinalStates());
        System.out.println("DFA Transition Function:");
        for (Map.Entry<String, Map<Character, String>> entry : dfa.getTransitionFunction().entrySet()) {
            String currentState = entry.getKey();
            Map<Character, String> transitions = entry.getValue();
            for (Map.Entry<Character, String> transition : transitions.entrySet()) {
                System.out.println(currentState + " --" + transition.getKey() + "--> " + transition.getValue());
            }
        }
    }
}