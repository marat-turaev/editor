package ru.spbau.turaev.editor.expression.operators;

abstract class BinaryExpression implements Expression {
    public Expression left;
    public Expression right;

    protected BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}