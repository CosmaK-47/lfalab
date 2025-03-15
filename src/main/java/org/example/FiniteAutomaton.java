package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FiniteAutomaton {
    private Map<String, Map<Character, Set<String>>> transitions;
    private Set<String> finalStates;

    public FiniteAutomaton(Map<String, Map<Character, Set<String>>> transitions, Set<String> finalStates) {
        this.transitions = transitions;
        this.finalStates = finalStates;
    }

    public boolean isDeterministic() {
        for (Map<Character, Set<String>> stateTransitions : transitions.values()) {
            for (Set<String> destinations : stateTransitions.values()) {
                if (destinations.size() > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public FiniteAutomaton convertToDFA() {
        Map<Set<String>, Map<Character, Set<String>>> newTransitions = new HashMap<>();
        Queue<Set<String>> queue = new LinkedList<>();
        Set<Set<String>> visited = new HashSet<>();

        Set<String> startState = Set.of("q0");
        queue.add(startState);
        visited.add(startState);

        while (!queue.isEmpty()) {
            Set<String> currentState = queue.poll();
            Map<Character, Set<String>> newStateTransitions = new HashMap<>();

            for (Character symbol : Arrays.asList('a', 'b')) {
                Set<String> nextStates = new HashSet<>();
                for (String state : currentState) {
                    if (transitions.containsKey(state) && transitions.get(state).containsKey(symbol)) {
                        nextStates.addAll(transitions.get(state).get(symbol));
                    }
                }
                if (!nextStates.isEmpty()) {
                    newStateTransitions.put(symbol, nextStates);
                    if (!visited.contains(nextStates)) {
                        queue.add(nextStates);
                        visited.add(nextStates);
                    }
                }
            }
            newTransitions.put(currentState, newStateTransitions);
        }

        Map<String, Map<Character, Set<String>>> convertedTransitions = new HashMap<>();
        Map<Set<String>, String> stateNames = new HashMap<>();
        int stateCounter = 0;
        for (Set<String> state : newTransitions.keySet()) {
            String stateName = "q" + stateCounter++;
            stateNames.put(state, stateName);
            convertedTransitions.put(stateName, new HashMap<>());
        }
        for (Map.Entry<Set<String>, Map<Character, Set<String>>> entry : newTransitions.entrySet()) {
            String stateName = stateNames.get(entry.getKey());
            for (Map.Entry<Character, Set<String>> transition : entry.getValue().entrySet()) {
                convertedTransitions.get(stateName).put(transition.getKey(), Set.of(stateNames.get(transition.getValue())));
            }
        }

        Set<String> newFinalStates = new HashSet<>();
        for (Map.Entry<Set<String>, String> entry : stateNames.entrySet()) {
            if (!Collections.disjoint(entry.getKey(), finalStates)) {
                newFinalStates.add(entry.getValue());
            }
        }

        return new FiniteAutomaton(convertedTransitions, newFinalStates);
    }

    public Map<String, List<String>> toRegularGrammar() {
        Map<String, List<String>> grammar = new HashMap<>();
        for (Map.Entry<String, Map<Character, Set<String>>> entry : transitions.entrySet()) {
            String state = entry.getKey();
            for (Map.Entry<Character, Set<String>> trans : entry.getValue().entrySet()) {
                for (String nextState : trans.getValue()) {
                    String rule = trans.getKey() + nextState;
                    grammar.computeIfAbsent(state, k -> new ArrayList<>()).add(rule);
                }
            }
        }
        return grammar;
    }

    public void printTransitions(String title) {
        System.out.println("\n" + title);
        for (Map.Entry<String, Map<Character, Set<String>>> entry : transitions.entrySet()) {
            String state = entry.getKey();
            for (Map.Entry<Character, Set<String>> trans : entry.getValue().entrySet()) {
                System.out.println("  " + state + " --" + trans.getKey() + "--> " + trans.getValue());
            }
        }
    }


    public void generateDOTFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("digraph FiniteAutomaton {\n");
            writer.write("    rankdir=LR;\n");
            writer.write("    node [shape = doublecircle]; ");
            for (String finalState : finalStates) {
                writer.write(finalState + " ");
            }
            writer.write(";\n");
            writer.write("    node [shape = circle];\n");
            for (Map.Entry<String, Map<Character, Set<String>>> entry : transitions.entrySet()) {
                String state = entry.getKey();
                for (Map.Entry<Character, Set<String>> trans : entry.getValue().entrySet()) {
                    for (String nextState : trans.getValue()) {
                        writer.write("    " + state + " -> " + nextState + " [label=\"" + trans.getKey() + "\"];\n");
                    }
                }
            }
            writer.write("}\n");
        }
    }
}
