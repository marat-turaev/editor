package ru.spbau.turaev.editor.expression;

public class Identifier implements Expression {
    public final String name;

    public Identifier(String name) {
        this.name = name;
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
