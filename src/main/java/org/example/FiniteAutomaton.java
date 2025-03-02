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
        Set<String> visited = new HashSet<>();
        queue.add(startSymbol);
        visited.add(startSymbol);

        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            if (currentState.equals(input)) {
                return true;
            }
            if (currentState.length() > input.length()) {
                continue;
            }
            for (Map.Entry<String, List<String>> entry : productionRules.entrySet()) {
                String nonTerminal = entry.getKey();
                List<String> productions = entry.getValue();
                if (currentState.contains(nonTerminal)) {
                    for (String production : productions) {
                        String newState = currentState.replaceFirst(nonTerminal, production);
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

    // Add this method inside the FiniteAutomaton class
    public Grammar toRegularGrammar() {
        Map<String, List<String>> grammarRules = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : productionRules.entrySet()) {
            String state = entry.getKey();
            List<String> transitions = entry.getValue();

            if (!grammarRules.containsKey(state)) {
                grammarRules.put(state, new ArrayList<>());
            }

            for (String transition : transitions) {
                // Check if transition follows A → aB or A → a
                if (transition.length() == 1 && Character.isLowerCase(transition.charAt(0))) {
                    // Terminal transition: A → a
                    grammarRules.get(state).add(transition);
                } else if (transition.length() == 2 &&
                        Character.isLowerCase(transition.charAt(0)) &&
                        Character.isUpperCase(transition.charAt(1))) {
                    // Non-terminal transition: A → aB
                    grammarRules.get(state).add(transition);
                }
            }
        }

        return new Grammar(alphabet, grammarRules, startSymbol);
    }

}
