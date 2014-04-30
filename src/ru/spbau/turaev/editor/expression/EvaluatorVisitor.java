package ru.spbau.turaev.editor.expression;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.expression.operators.MathBinaryOperation;

/**
 * Created by marat on 01/05/14.
 */
public interface EvaluatorVisitor extends ExpressionVisitor {
    Expression visit(MathBinaryOperation div);

}
