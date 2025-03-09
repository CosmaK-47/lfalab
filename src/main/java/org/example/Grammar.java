package org.example;
import java.util.*;

public class Grammar {
    private Set<String> nonTerminals;
    private Set<Character> terminals;
    private Map<String, List<String>> productions; // Private field
    private String startSymbol;

    public Grammar(Set<String> nonTerminals, Set<Character> terminals,
                   Map<String, List<String>> productions, String startSymbol) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = productions;
        this.startSymbol = startSymbol;
    }

    public boolean isRegularGrammar() {
        for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
            String lhs = entry.getKey();
            List<String> rhsList = entry.getValue();

            // Ensure LHS is a single non-terminal
            if (!nonTerminals.contains(lhs)) {
                return false;
            }

            // Check each RHS production
            for (String rhs : rhsList) {
                if (!isValidRegularRHS(rhs)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidRegularRHS(String rhs) {
        if (rhs.isEmpty()) {
            return true; // Empty string is allowed
        }

        if (rhs.length() == 1 && terminals.contains(rhs.charAt(0))) {
            return true; // Single terminal is allowed
        }

        if (rhs.length() == 2 &&
                terminals.contains(rhs.charAt(0)) && // First character must be a terminal
                nonTerminals.contains(rhs.substring(1))) { // Second character must be a non-terminal
            return true;
        }

        return false;
    }

    public String classifyGrammar() {
        if (isRegularGrammar()) {
            return "Type 3: Regular Grammar";
        } else {
            return "Not a Regular Grammar";
        }
    }

    // Add a getter for the productions field
    public Map<String, List<String>> getProductions() {
        return productions;
    }
}