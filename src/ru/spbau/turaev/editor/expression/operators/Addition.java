package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public class Addition extends MathBinaryOperation {
    public Addition(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Num doOperation(Num left, Num right) {
        return new Num(left.number.doubleValue() + right.number.doubleValue());
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
