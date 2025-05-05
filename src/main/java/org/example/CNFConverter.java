package org.example;

import java.util.*;

public class CNFConverter {

    public static void eliminateEpsilonProductions(Grammar grammar) {
        System.out.println("\n--- Eliminating ε-productions ---");

        Set<String> nullable = new HashSet<>();
        for (Rule rule : grammar.productions) {
            if (rule.right.size() == 1 && rule.right.get(0).equals("ε")) {
                nullable.add(rule.left);
            }
        }

        System.out.println("Nullable non-terminals: " + nullable);

        List<Rule> newRules = new ArrayList<>();
        for (Rule rule : grammar.productions) {
            if (rule.right.contains("ε")) continue;

            newRules.add(rule);
            for (int i = 0; i < rule.right.size(); i++) {
                if (nullable.contains(rule.right.get(i))) {
                    List<String> modified = new ArrayList<>(rule.right);
                    modified.remove(i);
                    if (!modified.isEmpty()) {
                        Rule newRule = new Rule(rule.left, modified);
                        if (!newRules.contains(newRule)) {
                            newRules.add(newRule);
                        }
                    }
                }
            }
        }

        grammar.productions = newRules;
        grammar.printGrammar();
    }

    public static void eliminateUnitProductions(Grammar grammar) {
        System.out.println("\n--- Eliminating Unit Productions ---");

        Set<Rule> result = new HashSet<>();
        Map<String, Set<String>> unitPairs = new HashMap<>();

        for (String nonTerminal : grammar.nonTerminals) {
            unitPairs.put(nonTerminal, new HashSet<>());
            unitPairs.get(nonTerminal).add(nonTerminal);  // A -> A
        }

        // Step 1: Find all unit pairs (A, B) where A can derive B via unit rules
        boolean changed;
        do {
            changed = false;
            for (Rule rule : grammar.productions) {
                if (rule.right.size() == 1 && grammar.nonTerminals.contains(rule.right.get(0))) {
                    String A = rule.left;
                    String B = rule.right.get(0);
                    for (String C : unitPairs.get(B)) {
                        if (unitPairs.get(A).add(C)) {
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);

        // Step 2: For each unit pair (A, B), add all non-unit productions of B to A
        for (String A : grammar.nonTerminals) {
            for (String B : unitPairs.get(A)) {
                for (Rule rule : grammar.productions) {
                    if (rule.left.equals(B) && !(rule.right.size() == 1 && grammar.nonTerminals.contains(rule.right.get(0)))) {
                        result.add(new Rule(A, rule.right));
                    }
                }
            }
        }

        grammar.productions = new ArrayList<>(result);
        grammar.printGrammar();
    }


    public static void eliminateInaccessibleSymbols(Grammar grammar) {
        System.out.println("\n--- Eliminating Inaccessible Symbols ---");

        Set<String> reachable = new HashSet<>();
        reachable.add(grammar.startSymbol);

        boolean changed;
        do {
            changed = false;
            for (Rule rule : grammar.productions) {
                if (reachable.contains(rule.left)) {
                    for (String symbol : rule.right) {
                        if (grammar.nonTerminals.contains(symbol) && !reachable.contains(symbol)) {
                            reachable.add(symbol);
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);

        grammar.productions.removeIf(rule -> !reachable.contains(rule.left));
        grammar.nonTerminals.retainAll(reachable);

        grammar.printGrammar();
    }

    public static void eliminateNonProductiveSymbols(Grammar grammar) {
        System.out.println("\n--- Eliminating Non-Productive Symbols ---");

        Set<String> productive = new HashSet<>();
        for (Rule rule : grammar.productions) {
            if (rule.right.stream().allMatch(sym -> grammar.terminals.contains(sym))) {
                productive.add(rule.left);
            }
        }

        boolean changed;
        do {
            changed = false;
            for (Rule rule : grammar.productions) {
                if (!productive.contains(rule.left) &&
                        rule.right.stream().allMatch(sym -> grammar.terminals.contains(sym) || productive.contains(sym))) {
                    productive.add(rule.left);
                    changed = true;
                }
            }
        } while (changed);

        grammar.productions.removeIf(rule -> !productive.contains(rule.left));
        grammar.nonTerminals.retainAll(productive);

        grammar.printGrammar();
    }

    public static void convertToCNF(Grammar grammar) {
        System.out.println("\n--- Converting to Chomsky Normal Form ---");

        List<Rule> originalRules = new ArrayList<>(grammar.productions);
        List<Rule> newRules = new ArrayList<>();
        int[] terminalVarIndex = {1};  // ✅ Make it an array so it’s mutable in lambda
        int cnfVarIndex = 1;
        Map<String, String> terminalMap = new HashMap<>();

        for (Rule rule : originalRules) {
            List<String> rhs = new ArrayList<>(rule.right);

            // Replace terminals in long RHS with new variables
            if (rhs.size() > 1) {
                for (int i = 0; i < rhs.size(); i++) {
                    String symbol = rhs.get(i);
                    if (grammar.terminals.contains(symbol)) {
                        String newVar = terminalMap.computeIfAbsent(symbol, k -> {
                            String var = "T" + terminalVarIndex[0]++; // ✅ Use array to mutate
                            grammar.nonTerminals.add(var);
                            newRules.add(new Rule(var, List.of(k)));
                            return var;
                        });
                        rhs.set(i, newVar);
                    }
                }

                // Break down RHS into binary rules
                while (rhs.size() > 2) {
                    String newVar = "X" + cnfVarIndex++;
                    grammar.nonTerminals.add(newVar);
                    List<String> pair = List.of(rhs.remove(0), rhs.remove(0));
                    newRules.add(new Rule(newVar, pair));
                    rhs.add(0, newVar);
                }
            }

            newRules.add(new Rule(rule.left, rhs));
        }

        grammar.productions = newRules;
        grammar.printGrammar();
    }

    public static void normalize(Grammar grammar) {
        eliminateEpsilonProductions(grammar);
        eliminateUnitProductions(grammar);
        eliminateInaccessibleSymbols(grammar);
        eliminateNonProductiveSymbols(grammar);
        convertToCNF(grammar);
    }
}
