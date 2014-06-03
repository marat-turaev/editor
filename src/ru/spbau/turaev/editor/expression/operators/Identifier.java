package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public class Identifier implements Expression {
    public final String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public Expression evaluate(EvaluatorVisitor visitor) throws UndefinedVariableException {
        return visitor.visit(this);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

}
