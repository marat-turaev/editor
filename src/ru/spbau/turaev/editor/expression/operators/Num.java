package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.ExpVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;

public class Num implements Expression {
    public final Number number;

    public Num(Number number) {
        this.number = number;
    }

    @Override
    public Expression accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        visitor.visit(this);
    }
}
