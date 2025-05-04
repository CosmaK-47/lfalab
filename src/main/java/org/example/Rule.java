package org.example;
import java.util.*;

public class Rule {
    public String left;
    public List<String> right;

    public Rule(String left, List<String> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left + " -> " + String.join("", right);
    }
}