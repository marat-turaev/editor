package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.operators.*;

public class Simplifier implements ExpressionVisitor {
    @Override
    public Expression visit(Num num) {
        return num;
    }

    @Override
    public Expression visit(Identifier identifier) {
        return identifier;
    }

    @Override
    public Expression visit(Equality equality) {
        equality.right = equality.right.accept(this);
        return equality;
    }

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
