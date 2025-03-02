package org.example;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Map<String, List<String>> productionRules = new HashMap<>();

        // type 0
        productionRules.put("S", Arrays.asList("aSb", "SS", ""));
        productionRules.put("A", Arrays.asList("BA"));
        productionRules.put("BA", Arrays.asList("C"));
        productionRules.put("C", Arrays.asList("a", "b", "ε"));

        //type 1
      //  productionRules.put("S", Arrays.asList("aSBC", "aBC"));
      //  productionRules.put("CB", Arrays.asList("BC"));
      //  productionRules.put("aB", Arrays.asList("ab"));
      //  productionRules.put("bC", Arrays.asList("bc"));

        //type 2
      //  productionRules.put("S", Arrays.asList("aA", "bB"));
      //  productionRules.put("A", Arrays.asList("a", "S"));
      //  productionRules.put("B", Arrays.asList("b", "S"));

        //type 3
      //  productionRules.put("S", Arrays.asList("aA", "bB"));
      //  productionRules.put("A", Arrays.asList("a", "aS"));
      //  productionRules.put("B", Arrays.asList("b", "bS"));


        Grammar grammar = new Grammar(alphabet, productionRules, "S");
        FiniteAutomaton fa = grammar.toFiniteAutomaton();

        System.out.println("Grammar classification: " + grammar.classifyGrammar());
        Grammar regularGrammar = fa.toRegularGrammar();
        System.out.println("Converted FA to Regular Grammar: " + regularGrammar.getProductionRules());

        while (true) {
            System.out.println("Enter a string to validate (or type 'exit' to quit):");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;
            System.out.println("Validating '" + input + "': " + fa.validateString(input));
        }

        scanner.close();
    }
}
