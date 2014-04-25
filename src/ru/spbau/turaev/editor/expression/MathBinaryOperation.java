package ru.spbau.turaev.editor.expression;

public abstract class MathBinaryOperation extends BinaryExpression {
    protected MathBinaryOperation(Expression left, Expression right) {
        super(left, right);
    }

    public abstract Num doOperation(Num left, Num right);

    public final Expression simplify() {
        if (left instanceof Num && right instanceof Num) {
            return doOperation((Num) left, (Num) right);
        }

        return this;
    }
}
