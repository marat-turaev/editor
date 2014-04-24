package ru.spbau.turaev.editor.expression;

public class Num extends MonoExpression {
    public final Number number;

    public Num(Number number) {
        this.number = number;
    }

    @Override
    public void accept(ExpVisitor prettyPrinter) {
        prettyPrinter.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        visitor.visit(this);
    }
}
