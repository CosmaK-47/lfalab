package org.example;
import java.util.*;

public class FiniteAutomaton {
    private Map<String, List<String>> productionRules;
    private String startSymbol;
    private Set<Character> alphabet;

    public FiniteAutomaton(Grammar grammar) {
        this.productionRules = grammar.getProductionRules();
        this.startSymbol = grammar.getStartSymbol();
        this.alphabet = grammar.getAlphabet();
    }

    public boolean validateString(String input) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>(); // Avoid reprocessing the same state
        queue.add(startSymbol);
        visited.add(startSymbol);

        while (!queue.isEmpty()) {
            String currentState = queue.poll();

            // If we reached a purely terminal string, check match
            if (currentState.equals(input)) {
                return true;
            }

            // If the generated string is already longer than input, skip it
            if (currentState.length() > input.length()) {
                continue;
            }

            // Expand non-terminals based on grammar rules
            for (Map.Entry<String, List<String>> entry : productionRules.entrySet()) {
                String nonTerminal = entry.getKey();
                List<String> productions = entry.getValue();

                if (currentState.contains(nonTerminal)) {
                    for (String production : productions) {
                        String newState = currentState.replaceFirst(nonTerminal, production);

                        // Avoid revisiting already seen states
                        if (!visited.contains(newState)) {
                            queue.add(newState);
                            visited.add(newState);
                        }
                    }
                }
            }
        }

        return false;
    }
}
