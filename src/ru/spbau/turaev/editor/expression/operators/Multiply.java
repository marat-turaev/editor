package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.ExpVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;

public class Multiply extends MathBinaryOperation  {
    public Multiply(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Num doOperation(Num left, Num right) {
        return new Num(left.number.doubleValue() * right.number.doubleValue());
    }

    @Override
    public Expression accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}
