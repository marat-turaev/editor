package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public class Equality implements Expression {
    public final Identifier left;
    public Expression right;

    public Equality(Identifier left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression evaluate(EvaluatorVisitor visitor) throws UndefinedVariableException {
        return visitor.visit(this);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void traverse(ExpressionVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}
