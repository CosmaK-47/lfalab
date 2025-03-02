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

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public Map<String, List<String>> getProductionRules() {
        return productionRules;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public List<String> generateValidStrings(int count) {
        List<String> validStrings = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            String current = startSymbol;
            while (containsNonTerminal(current)) {
                for (String key : productionRules.keySet()) {
                    if (current.contains(key)) {
                        List<String> rules = productionRules.get(key);
                        current = current.replaceFirst(key, rules.get(rand.nextInt(rules.size())));
                    }
                }
            }
            validStrings.add(current);
        }
        return validStrings;
    }

    private boolean containsNonTerminal(String str) {
        return productionRules.keySet().stream().anyMatch(str::contains);
    }

    public FiniteAutomaton toFiniteAutomaton() {
        return new FiniteAutomaton(this);
    }

    public String classifyGrammar() {
        boolean isRegular = true;
        boolean isContextFree = true;
        boolean isContextSensitive = true;

        for (Map.Entry<String, List<String>> entry : productionRules.entrySet()) {
            String left = entry.getKey();
            List<String> rightList = entry.getValue();

            for (String right : rightList) {
                // Check left side (should be 1 non-terminal for Regular and Context-Free)
                if (left.length() > 1) {
                    isRegular = false;
                    isContextFree = false;
                }

                // Check right side for Regular Grammar (A → aB or A → a)
                if (isRegular) {
                    if (!(right.matches("[a-z]") || right.matches("[a-z][A-Z]"))) {
                        isRegular = false;
                    }
                }

                // Context-Sensitive: RHS must be at least as long as LHS
                if (right.length() < left.length() && !right.equals("")) {
                    isContextSensitive = false;
                }
            }
        }

        if (isRegular) return "Type 3 (Regular Grammar)";
        if (isContextFree) return "Type 2 (Context-Free Grammar)";
        if (isContextSensitive) return "Type 1 (Context-Sensitive Grammar)";
        return "Type 0 (Unrestricted Grammar)";
    }

}