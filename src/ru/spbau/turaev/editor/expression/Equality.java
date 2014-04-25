package ru.spbau.turaev.editor.expression;

public class Equality extends BinaryExpression {
    public Equality(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression accept(ExpReworkerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}
