package org.example;
import java.util.*;

public class FiniteAutomaton {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, Map<Character, Set<String>>> transitionFunction;
    private String startState;
    private Set<String> finalStates;

    public FiniteAutomaton(Set<String> states, Set<Character> alphabet,
                           Map<String, Map<Character, Set<String>>> transitionFunction,
                           String startState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitionFunction = transitionFunction;
        this.startState = startState;
        this.finalStates = finalStates;
    }

    public Grammar convertToRegularGrammar() {
        Set<String> nonTerminals = new HashSet<>(states);
        Set<Character> terminals = new HashSet<>(alphabet);
        Map<String, List<String>> productions = new HashMap<>();

        for (String state : states) {
            productions.put(state, new ArrayList<>());

            if (transitionFunction.containsKey(state)) {
                for (Map.Entry<Character, Set<String>> entry : transitionFunction.get(state).entrySet()) {
                    char symbol = entry.getKey();
                    for (String nextState : entry.getValue()) {
                        productions.get(state).add(symbol + nextState);
                    }
                }
            }

            if (finalStates.contains(state)) {
                productions.get(state).add(""); // Add epsilon production
            }
        }

        return new Grammar(nonTerminals, terminals, productions, startState);
    }

    public boolean isDeterministic() {
        for (Map<Character, Set<String>> transitions : transitionFunction.values()) {
            for (Set<String> nextStates : transitions.values()) {
                if (nextStates.size() > 1) {
                    return false; // Non-deterministic if multiple transitions exist
                }
            }
        }
        return true;
    }

    public DFA convertNDFAtoDFA() {
        Set<String> dfaStates = new HashSet<>();
        Set<Character> dfaAlphabet = new HashSet<>(alphabet);
        Map<String, Map<Character, String>> dfaTransitionFunction = new HashMap<>();
        String dfaStartState = "{" + String.join(",", getEpsilonClosure(Set.of(startState))) + "}";
        Set<String> dfaFinalStates = new HashSet<>();

        Queue<Set<String>> queue = new LinkedList<>();
        Map<Set<String>, String> stateMapping = new HashMap<>();

        // Initialize with the epsilon closure of the start state
        Set<String> startStateClosure = getEpsilonClosure(Set.of(startState));
        queue.add(startStateClosure);
        stateMapping.put(startStateClosure, dfaStartState);

        while (!queue.isEmpty()) {
            Set<String> currentSet = queue.poll();
            String currentState = stateMapping.get(currentSet);

            if (dfaStates.add(currentState)) {
                if (!Collections.disjoint(currentSet, finalStates)) {
                    dfaFinalStates.add(currentState);
                }

                Map<Character, String> transitions = new HashMap<>();
                for (Character symbol : alphabet) {
                    Set<String> nextSet = new HashSet<>();
                    for (String state : currentSet) {
                        if (transitionFunction.containsKey(state) && transitionFunction.get(state).containsKey(symbol)) {
                            nextSet.addAll(transitionFunction.get(state).get(symbol));
                        }
                    }
                    nextSet = getEpsilonClosure(nextSet);

                    if (!nextSet.isEmpty()) {
                        String nextState = stateMapping.computeIfAbsent(nextSet, k -> "{" + String.join(",", k) + "}");
                        transitions.put(symbol, nextState);
                        if (!dfaStates.contains(nextState)) {
                            queue.add(nextSet);
                        }
                    }
                }

                dfaTransitionFunction.put(currentState, transitions);
            }
        }

        return new DFA(dfaStates, dfaAlphabet, dfaTransitionFunction, dfaStartState, dfaFinalStates);
    }

    private Set<String> getEpsilonClosure(Set<String> states) {
        Set<String> closure = new HashSet<>(states);
        Queue<String> queue = new LinkedList<>(states);

        while (!queue.isEmpty()) {
            String currentState = queue.poll();
            if (transitionFunction.containsKey(currentState) && transitionFunction.get(currentState).containsKey('\0')) {
                for (String nextState : transitionFunction.get(currentState).get('\0')) {
                    if (closure.add(nextState)) {
                        queue.add(nextState);
                    }
                }
            }
        }

        return closure;
    }
}