package ru.spbau.turaev.editor.expression;

public class Sum extends MathBinaryOperation {
    public Sum(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Num doOperation(Num left, Num right) {
        return new Num(left.number.doubleValue() + right.number.doubleValue());
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
