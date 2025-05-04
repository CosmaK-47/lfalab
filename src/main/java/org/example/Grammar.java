package org.example;
import java.util.*;

public class Grammar {
    public Set<String> nonTerminals = new HashSet<>();
    public Set<String> terminals = new HashSet<>();
    public String startSymbol;
    public List<Rule> productions = new ArrayList<>();

    public Grammar(String startSymbol) {
        this.startSymbol = startSymbol;
    }

    public void addProduction(String left, String right) {
        nonTerminals.add(left);
        List<String> symbols = new ArrayList<>();
        for (int i = 0; i < right.length(); i++) {
            String ch = String.valueOf(right.charAt(i));
            symbols.add(ch);
        }
        productions.add(new Rule(left, symbols));
    }

    public void printGrammar() {
        for (Rule rule : productions) {
            System.out.println(rule);
        }
    }
}