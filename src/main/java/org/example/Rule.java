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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Rule rule = (Rule) obj;
        return left.equals(rule.left) && right.equals(rule.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
