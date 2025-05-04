# CNF Grammar Normalization Report

## Course: Formal Languages & Finite Automata
**Author:** Usurelu Cosmin

---

## **Overview**
This laboratory work explores the process of **normalizing context-free grammars into Chomsky Normal Form (CNF)**. The CNF format simplifies grammar rules to make them more suitable for parsing algorithms such as CYK.  
The main goals were to understand the theoretical transformation steps and implement them in Java as a reusable and modular function.

---

## **Objectives**

1. Learn about Chomsky Normal Form (CNF) [1].
2. Get familiar with the approaches of normalizing a grammar.
3. Implement a method for normalizing an input grammar by the rules of CNF.
4. Encapsulate the normalization logic in a clean, reusable Java class/method.
5. Execute and test the implementation using a predefined grammar.
6. **(Bonus)** Make the function work for any valid grammar input, not just the assigned variant.

---

## **Implementation**

### Main Components

- **Rule Class:** Represents a grammar rule with a left-hand non-terminal and a right-hand side list of symbols.
- **Grammar Class:** Stores terminals, non-terminals, the start symbol, and a list of production rules. Provides methods for rule addition and printing.
- **CNFConverter Class:** Core logic that includes several steps to normalize the grammar into CNF, implemented as static methods for clarity and reusability.

### CNF Transformation Steps

The `normalize()` method encapsulates the entire CNF transformation pipeline by calling the following methods in sequence:

1. **eliminateEpsilonProductions:**
    - Identifies nullable symbols (those that produce ε) and eliminates such productions while generating all possible ε-free alternatives.

2. **eliminateUnitProductions:**
    - Replaces rules of the form A → B (where both are non-terminals) with equivalent rules derived from B's productions.

3. **eliminateInaccessibleSymbols:**
    - Removes non-terminals that are not reachable from the start symbol.

4. **eliminateNonProductiveSymbols:**
    - Filters out symbols that cannot derive a terminal string.

5. **convertToCNF:**
    - Converts the remaining rules to CNF by:
        - Replacing terminals in long productions with helper non-terminals (e.g., T1 → a).
        - Breaking down right-hand sides longer than 2 symbols into binary rules using new variables (e.g., A → BCD becomes A → B X1, X1 → C D).

---

## **Testing**

A sample grammar was constructed and passed into the CNF normalizer:

```java
grammar.terminals.addAll(Set.of("a", "b", "d"));
grammar.addProduction("S", "dB");
grammar.addProduction("S", "A");
grammar.addProduction("A", "d");
grammar.addProduction("A", "dS");
grammar.addProduction("A", "aBdB");
grammar.addProduction("B", "a");
grammar.addProduction("B", "aS");
grammar.addProduction("B", "AC");
grammar.addProduction("D", "AB");
grammar.addProduction("C", "bC");
grammar.addProduction("C", "ε");
```

Output:
```
Original Grammar:
rust
Copy
Edit
S -> dB
S -> A
A -> d
A -> dS
A -> aBdB
B -> a
B -> aS
B -> AC
D -> AB
C -> bC
C -> ε
```

## CNF Grammar:
The transformed grammar contained only:

Binary rules (e.g., X1 -> A B)

Terminal substitutions (e.g., T1 -> a, used in larger rules)

No ε-productions, unit productions, or inaccessible/non-productive rules.

Each rule was verified to comply with CNF standards:

All rules are of the form A → BC or A → a.

## Conclusion

This laboratory assignment effectively demonstrated the full process of transforming a context-free grammar into Chomsky Normal Form using Java. The result is a modular, well-structured implementation that:

- Handles arbitrary grammars, not just the one from the assigned variant.

- Breaks down complex transformations into logical steps.

- Reinforces understanding of grammar simplification and normalization.