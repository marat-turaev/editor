package ru.spbau.turaev.editor.expression;

public class Sum extends BiExp {
    public Sum(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public void accept(ExpVisitor prettyPrinter) {
        prettyPrinter.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}
