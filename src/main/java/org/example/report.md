# AST-Based Parsing Report

## Course: Formal Languages & Finite Automata
**Author:** Usurelu Cosmin

---

## **Overview**
This laboratory work explores the process of **parsing** — extracting syntactic structure from an input text — and building a corresponding **Abstract Syntax Tree (AST)**. While parsing often results in a parse tree that reflects grammatical structure, the AST provides a cleaner and more abstract representation of the program's semantics, removing unnecessary syntactic details.

This experiment builds upon the lexer implemented in the third laboratory, extending it with a **TokenType enum**, **AST node hierarchy**, and a **recursive descent parser** that constructs the AST from mathematical expressions.

---

## **Objectives**

1. Understand the concept and purpose of parsing [1].
2. Get familiar with the Abstract Syntax Tree (AST) and its usefulness in compilation [2].
3. Add a `TokenType` (enum) to categorize tokens recognized by the lexer.
4. Use **regular expressions** to detect token types (e.g., numbers, functions, operators).
5. Design and implement **AST node classes** to represent functions, numbers, and operations.
6. Build a **simple recursive parser** that constructs the AST from the token list.
7. Print and visually inspect the resulting AST structure for given inputs.

---

## **Implementation**

### Components

- **TokenType (enum):**  
  Enumerates possible token categories: `NUMBER`, `OPERATOR`, `FUNCTION`, `PARENTHESIS`, and `EOF`.

- **Lexer:**  
  Uses regex patterns to tokenize the input and match each token against a `TokenType`.

- **AST Node Classes:**  
  Includes a base `ASTNode` class and concrete implementations:
    - `NumberNode`: represents numeric values
    - `BinaryOpNode`: represents binary operations (+, -, *, /)
    - `FunctionNode`: represents function calls such as `sin(expr)`

- **Parser:**  
  Implements a **recursive descent parser** based on the following grammar:
  
- - expr → term ((‘+’ | ‘-’) term)*
  term → factor ((‘’ | ‘/’) factor)
  factor → FUNCTION '(' expr ')' | NUMBER | '(' expr ')'
    It processes the token list and builds a hierarchical AST representation.

---

## **Testing**

A few expressions were used to test the parser and inspect the AST output:

### Input:
```text
sin(3 + 4) * 2
```
## Output Tokens:
```
Token{type=FUNCTION, value='sin'}
Token{type=PARENTHESIS, value='('}
Token{type=NUMBER, value='3'}
Token{type=OPERATOR, value='+'}
Token{type=NUMBER, value='4'}
Token{type=PARENTHESIS, value=')'}
Token{type=OPERATOR, value='*'}
Token{type=NUMBER, value='2'}
Token{type=EOF, value=''}
```

## Output AST:
```BinaryOp(*)
  Function(sin)
    BinaryOp(+)
      Number(3)
      Number(4)
  Number(2)
```

## Conclusion
This laboratory assignment demonstrated how to extend a lexical analyzer with parsing logic and build an Abstract Syntax Tree from structured input. The result is a clean, modular Java program that:

- Categorizes tokens using regular expressions.

- Builds an AST using recursive parsing logic.

- Supports real mathematical expressions including functions like sin and cos.

- Outputs a human-readable tree structure, allowing easy debugging and future extensions such as interpretation or code generation.