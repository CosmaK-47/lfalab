package org.example;
import java.util.*;

public class CNFConverter {

    public static void eliminateEpsilonProductions(Grammar grammar) {
        Set<String> nullable = new HashSet<>();
        for (Rule rule : grammar.productions) {
            if (rule.right.size() == 1 && rule.right.get(0).equals("ε")) {
                nullable.add(rule.left);
            }
        }

        List<Rule> newRules = new ArrayList<>();
        for (Rule rule : grammar.productions) {
            if (rule.right.contains("ε")) continue;
            newRules.add(rule);
            for (int i = 0; i < rule.right.size(); i++) {
                if (nullable.contains(rule.right.get(i))) {
                    List<String> modified = new ArrayList<>(rule.right);
                    modified.remove(i);
                    if (!modified.isEmpty()) {
                        newRules.add(new Rule(rule.left, modified));
                    }
                }
            }
        }
        grammar.productions = newRules;
    }

    public static void eliminateUnitProductions(Grammar grammar) {
        boolean changed;
        do {
            changed = false;
            List<Rule> newRules = new ArrayList<>();
            for (Rule rule : grammar.productions) {
                if (rule.right.size() == 1 && grammar.nonTerminals.contains(rule.right.get(0))) {
                    String target = rule.right.get(0);
                    for (Rule r : grammar.productions) {
                        if (r.left.equals(target) && !r.left.equals(rule.left)) {
                            newRules.add(new Rule(rule.left, r.right));
                            changed = true;
                        }
                    }
                } else {
                    newRules.add(rule);
                }
            }
            grammar.productions = newRules;
        } while (changed);
    }

    public static void eliminateInaccessibleSymbols(Grammar grammar) {
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
    }

    public static void eliminateNonProductiveSymbols(Grammar grammar) {
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
    }

    public static void convertToCNF(Grammar grammar) {
        List<Rule> originalRules = new ArrayList<>(grammar.productions);
        List<Rule> newRules = new ArrayList<>();
        int tempVarIndex = 1;

        // Step 1: Replace terminals in RHS of length > 1 with new non-terminals
        for (Rule rule : originalRules) {
            List<String> rhs = new ArrayList<>(rule.right);

            if (rhs.size() > 1) {
                for (int i = 0; i < rhs.size(); i++) {
                    String symbol = rhs.get(i);
                    if (grammar.terminals.contains(symbol)) {
                        String newVar = "T" + tempVarIndex++;
                        grammar.nonTerminals.add(newVar);
                        // Only add new terminal rule if it doesn't already exist
                        Rule terminalRule = new Rule(newVar, List.of(symbol));
                        if (!grammar.productions.contains(terminalRule)) {
                            newRules.add(terminalRule);
                        }
                        rhs.set(i, newVar);
                    }
                }

                // Step 2: Break into binary rules if longer than 2
                while (rhs.size() > 2) {
                    String newVar = "X" + tempVarIndex++;
                    grammar.nonTerminals.add(newVar);
                    List<String> pair = List.of(rhs.remove(0), rhs.remove(0));
                    newRules.add(new Rule(newVar, pair));
                    rhs.add(0, newVar);
                }
            }

            newRules.add(new Rule(rule.left, rhs));
        }

        grammar.productions = newRules;
    }


    public static void normalize(Grammar grammar) {
        eliminateEpsilonProductions(grammar);
        eliminateUnitProductions(grammar);
        eliminateInaccessibleSymbols(grammar);
        eliminateNonProductiveSymbols(grammar);
        convertToCNF(grammar);
    }
}