package ru.spbau.turaev.editor.expression;

public class Num implements Expression {
    public final Number number;

    public Num(Number number) {
        this.number = number;
    }

    @Override
    public Expression accept(ExpReworkerVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        visitor.visit(this);
    }
}
