package ru.spbau.turaev.editor.expression;

public abstract class MonoExpression implements Expression {
    @Override
    public Expression simplify() {
        return this;
    }
}
