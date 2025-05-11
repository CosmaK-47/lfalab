package org.example;

class BinaryOpNode extends ASTNode {
    String operator;
    ASTNode left, right;

    public BinaryOpNode(String operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "BinaryOp(" + operator + ")");
        left.print(indent + "  ");
        right.print(indent + "  ");
    }
}
