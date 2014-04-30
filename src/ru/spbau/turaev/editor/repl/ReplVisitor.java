package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.operators.*;

public abstract class ReplVisitor implements ExpressionVisitor {
    @Override
    public Expression visit(Sum sum) {
        return visitBinaryExpression(sum);
    }

    @Override
    public Expression visit(Sub sub) {
        return visitBinaryExpression(sub);
    }

    @Override
    public Expression visit(Multiply multiply) {
        return visitBinaryExpression(multiply);
    }

    @Override
    public Expression visit(Div div) {
        return visitBinaryExpression(div);
    }

    private Expression visitBinaryExpression(MathBinaryOperation binaryExpression) {
        binaryExpression.left = binaryExpression.left.accept(this);
        binaryExpression.right = binaryExpression.right.accept(this);
        return binaryExpression.simplify();
    }
}
