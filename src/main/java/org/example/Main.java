package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Hardcoded alphabet and production rules
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Map<String, List<String>> productionRules = new HashMap<>();
        productionRules.put("S", Arrays.asList("aA", "bB"));
        productionRules.put("A", Arrays.asList("a", "S"));
        productionRules.put("B", Arrays.asList("b", "S"));

        Grammar grammar = new Grammar(alphabet, productionRules, "S");
        FiniteAutomaton fa = grammar.toFiniteAutomaton();

        // Allow user input for string validation
        while (true) {
            System.out.println("Enter a string to validate (or type 'exit' to quit):");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            System.out.println("Validating '" + input + "': " + fa.validateString(input));
        }

        scanner.close();
    }
}
