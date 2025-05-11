package org.example;

class FunctionNode extends ASTNode {
    String name;
    ASTNode argument;

    public FunctionNode(String name, ASTNode argument) {
        this.name = name;
        this.argument = argument;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "Function(" + name + ")");
        argument.print(indent + "  ");
    }
}
