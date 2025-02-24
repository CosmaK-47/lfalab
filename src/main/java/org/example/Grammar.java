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
            while (containsNonTerminal(current)) { // Keep replacing non-terminals
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
}
