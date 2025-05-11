package org.example;

class NumberNode extends ASTNode {
    String value;

    public NumberNode(String value) {
        this.value = value;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Number(" + value + ")");
    }
}
