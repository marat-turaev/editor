package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.ExpVisitor;

public class Mul extends BiExp {
    public Mul(Exp left, Exp right) {
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
