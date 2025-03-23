package org.example;

import java.util.*;
import java.util.regex.*;

class Lexer {
    private String input;
    private int pos;

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?");
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("^(sin|cos)");
    private static final Pattern OPERATOR_PATTERN = Pattern.compile("^[+\\-*/]");
    private static final Pattern PARENTHESIS_PATTERN = Pattern.compile("^[()]");

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char current = input.charAt(pos);

            if (Character.isWhitespace(current)) {
                pos++; // Skip spaces
                continue;
            }

            // Match number
            Matcher numberMatcher = NUMBER_PATTERN.matcher(input.substring(pos));
            if (numberMatcher.find()) {
                String value = numberMatcher.group();
                tokens.add(new Token(TokenType.NUMBER, value));
                pos += value.length();
                continue;
            }

            // Match function (sin, cos)
            Matcher functionMatcher = FUNCTION_PATTERN.matcher(input.substring(pos));
            if (functionMatcher.find()) {
                String value = functionMatcher.group();
                tokens.add(new Token(TokenType.FUNCTION, value));
                pos += value.length();
                continue;
            }

            // Match operator (+, -, *, /)
            Matcher operatorMatcher = OPERATOR_PATTERN.matcher(input.substring(pos));
            if (operatorMatcher.find()) {
                String value = operatorMatcher.group();
                tokens.add(new Token(TokenType.OPERATOR, value));
                pos += value.length();
                continue;
            }

            // Match parentheses
            Matcher parenthesisMatcher = PARENTHESIS_PATTERN.matcher(input.substring(pos));
            if (parenthesisMatcher.find()) {
                String value = parenthesisMatcher.group();
                tokens.add(new Token(TokenType.PARENTHESIS, value));
                pos += value.length();
                continue;
            }

            throw new RuntimeException("Unexpected character: " + current);
        }

        // Add an EOF (End of File) token
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
