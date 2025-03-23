# Lexer & Scanner Report

## Course: Formal Languages & Finite Automata
**Author:** Usurelu Cosmin

---

## **Overview**
The process of lexical analysis, also known as tokenization, is the first step in language processing. The lexer (or scanner/tokenizer) reads an input string and converts it into meaningful units called tokens. These tokens are used in later stages of language processing, such as parsing and semantic analysis.

In this laboratory work, we implemented a **lexer** in Java that recognizes different types of tokens, including numbers, operators, parentheses, and functions like `sin` and `cos`. The implementation follows a structured approach, using **regular expressions** to identify different tokens.

---

## **Objectives**
1. Understand the concept of lexical analysis.
2. Implement a lexer that can process mathematical expressions.
3. Use Java’s regular expressions to extract tokens.
4. Structure the project according to best practices and document it accordingly.

---

## **Implementation**
The lexer processes an input string and identifies tokens using **regular expressions**. The tokens are classified into categories such as **numbers**, **operators**, **functions**, and **parentheses**. An **enum** is used to define token types, and a **token class** stores the type and value of each token.

The main components of the implementation include:
- **TokenType Enum:** Defines token categories.
- **Token Class:** Represents individual tokens.
- **Lexer Class:** Uses regular expressions to identify tokens and convert input into a structured list.
- **Main Program:** Accepts input, tokenizes it, and displays the results.

---

## **Results**
**Example Input:**
```
sin(3.14) + cos(2) * 5
```

**Expected Output:**
```
Tokens:
FUNCTION: sin
PARENTHESIS: (
NUMBER: 3.14
PARENTHESIS: )
OPERATOR: +
FUNCTION: cos
PARENTHESIS: (
NUMBER: 2
PARENTHESIS: )
OPERATOR: *
NUMBER: 5
EOF
```

---

## **Conclusion**
In this laboratory work, we successfully implemented a **lexer** that can tokenize mathematical expressions, including functions, numbers, operators, and parentheses. The use of **regular expressions** allowed us to efficiently recognize different token types.

Key takeaways:
- Lexical analysis is a fundamental step in **compilers and interpreters**.
- **Regular expressions** help in defining token patterns.
- **Enums** provide a structured way to classify tokens.
- **Error handling** is essential for robustness.

This lexer can be extended further by supporting **more operators, variables, and advanced mathematical functions**. Future work could also include **integrating it with a parser** to evaluate expressions.

---

## **References**
- [Lexical Analysis - Wikipedia](https://en.wikipedia.org/wiki/Lexical_analysis)
- [LLVM Tutorial - Implementing a Lexer](https://llvm.org/docs/tutorial/MyFirstLanguageFrontend/LangImpl01.html)

