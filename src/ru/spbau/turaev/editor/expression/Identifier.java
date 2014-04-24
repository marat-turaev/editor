package ru.spbau.turaev.editor.expression;

public class Identifier extends MonoExpression {
    public final String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public void accept(ExpVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        visitor.visit(this);
    }
}
