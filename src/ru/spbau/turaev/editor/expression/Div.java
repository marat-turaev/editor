package ru.spbau.turaev.editor.expression;

public class Div extends BiExp {
    public Div(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public void accept(ExpVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}