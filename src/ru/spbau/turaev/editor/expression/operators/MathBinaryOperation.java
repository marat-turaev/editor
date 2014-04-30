package ru.spbau.turaev.editor.expression.operators;

public abstract class MathBinaryOperation implements Expression {
    public Expression left;
    public Expression right;

    protected MathBinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public abstract Num doOperation(Num left, Num right);

    public final Expression simplify() {
        if (left instanceof Num && right instanceof Num) {
            return doOperation((Num) left, (Num) right);
        }

        return this;
    }
}
