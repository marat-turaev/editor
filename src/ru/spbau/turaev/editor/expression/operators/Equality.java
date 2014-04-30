package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.ExpVisitor;

public class Equality implements Expression {
    public final Identifier left;
    public Expression right;

    public Equality(Identifier left, Expression right) {
        this.left = left;
        this.right = right;
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
