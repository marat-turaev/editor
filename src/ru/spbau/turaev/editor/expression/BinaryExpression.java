package ru.spbau.turaev.editor.expression;

abstract class BinaryExpression implements Expression {
    public Expression left;
    public Expression right;

    protected BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression simplify() {
        left = left.simplify();
        right = right.simplify();

        return this;
    }
}
