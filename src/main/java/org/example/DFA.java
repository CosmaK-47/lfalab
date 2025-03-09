package org.example;
import java.util.*;

public class DFA {
    private Set<String> states;
    private Set<Character> alphabet;
    private Map<String, Map<Character, String>> transitionFunction;
    private String startState;
    private Set<String> finalStates;

    public DFA(Set<String> states, Set<Character> alphabet,
               Map<String, Map<Character, String>> transitionFunction,
               String startState, Set<String> finalStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitionFunction = transitionFunction;
        this.startState = startState;
        this.finalStates = finalStates;
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<Character> getAlphabet() {
        return alphabet;
    }

    public String getStartState() {
        return startState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public Map<String, Map<Character, String>> getTransitionFunction() {
        return transitionFunction;
    }
}