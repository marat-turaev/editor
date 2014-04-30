package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.ExpVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;

public class Identifier implements Expression {
    public final String name;

    public Identifier(String name) {
        this.name = name;
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
