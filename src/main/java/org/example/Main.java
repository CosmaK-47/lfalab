package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter an expression to tokenize and parse:");
        String input = scanner.nextLine();

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        System.out.println("\nTokens:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        System.out.println("\nAST Structure:");
        ast.print("");
    }
}
