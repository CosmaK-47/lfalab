package org.example;
import java.util.*;
import java.util.regex.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter an expression to tokenize:");
        String input = scanner.nextLine();

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        scanner.close();
    }
}
