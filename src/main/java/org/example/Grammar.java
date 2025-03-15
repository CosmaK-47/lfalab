package org.example;

import java.util.*;

public class Grammar {
    private Set<Character> alphabet;
    private Map<String, List<String>> productionRules;
    private String startSymbol;

    public Grammar(Set<Character> alphabet, Map<String, List<String>> productionRules, String startSymbol) {
        this.alphabet = alphabet;
        this.productionRules = productionRules;
        this.startSymbol = startSymbol;
    }

    public String classifyGrammar() {
        boolean hasNonRegularRules = false;
        boolean hasContextSensitiveRules = false;

        for (Map.Entry<String, List<String>> entry : productionRules.entrySet()) {
            String lhs = entry.getKey();
            for (String rhs : entry.getValue()) {
                if (lhs.length() > 1) {
                    hasContextSensitiveRules = true;
                }
                if (!rhs.matches("[a-zA-Z]*")) {
                    hasNonRegularRules = true;
                }
            }
        }

        if (hasContextSensitiveRules) return "Type 1: Context-Sensitive Grammar";
        if (hasNonRegularRules) return "Type 2: Context-Free Grammar";
        return "Type 3: Regular Grammar";
    }
}
