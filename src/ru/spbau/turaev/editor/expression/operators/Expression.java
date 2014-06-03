package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public interface Expression {
    public void accept(ExpressionVisitor visitor);

    public Expression evaluate(EvaluatorVisitor visitor) throws UndefinedVariableException;
}
