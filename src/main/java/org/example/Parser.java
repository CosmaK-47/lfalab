package org.example;

import java.util.*;

class Parser {
    private List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public ASTNode parse() {
        return parseExpr();
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token advance() {
        return tokens.get(pos++);
    }

    private boolean match(TokenType type, String value) {
        if (peek().type == type && (value == null || peek().value.equals(value))) {
            advance();
            return true;
        }
        return false;
    }

    private ASTNode parseExpr() {
        ASTNode node = parseTerm();
        while (peek().type == TokenType.OPERATOR &&
                (peek().value.equals("+") || peek().value.equals("-"))) {
            String op = advance().value;
            ASTNode right = parseTerm();
            node = new BinaryOpNode(op, node, right);
        }
        return node;
    }

    private ASTNode parseTerm() {
        ASTNode node = parseFactor();
        while (peek().type == TokenType.OPERATOR &&
                (peek().value.equals("*") || peek().value.equals("/"))) {
            String op = advance().value;
            ASTNode right = parseFactor();
            node = new BinaryOpNode(op, node, right);
        }
        return node;
    }

    private ASTNode parseFactor() {
        Token token = peek();

        if (token.type == TokenType.FUNCTION) {
            String funcName = advance().value;
            if (!match(TokenType.PARENTHESIS, "(")) {
                throw new RuntimeException("Expected '(' after function name");
            }
            ASTNode arg = parseExpr();
            if (!match(TokenType.PARENTHESIS, ")")) {
                throw new RuntimeException("Expected ')' after function argument");
            }
            return new FunctionNode(funcName, arg);
        }

        if (token.type == TokenType.NUMBER) {
            return new NumberNode(advance().value);
        }

        if (match(TokenType.PARENTHESIS, "(")) {
            ASTNode node = parseExpr();
            if (!match(TokenType.PARENTHESIS, ")")) {
                throw new RuntimeException("Expected ')'");
            }
            return node;
        }

        throw new RuntimeException("Unexpected token: " + token);
    }
}
