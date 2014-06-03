package ru.spbau.turaev.editor.repl.visitors;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.operators.*;
import ru.spbau.turaev.editor.repl.Context;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public abstract class ReplVisitor implements EvaluatorVisitor {
    protected Context context;

    public ReplVisitor(Context context) {
        this.context = context;
    }

    @Override
    public Expression visit(Identifier identifier) throws UndefinedVariableException {
        if (context.hasValue(identifier)) {
            return context.getValue(identifier).evaluate(this);
        }
        return identifier;
    }

    @Override
    public Expression visit(Addition addition) throws UndefinedVariableException {
        return visitBinaryExpression(addition);
    }

    @Override
    public Expression visit(Subtraction subtraction) throws UndefinedVariableException {
        return visitBinaryExpression(subtraction);
    }

    @Override
    public Expression visit(Multiplication multiplication) throws UndefinedVariableException {
        return visitBinaryExpression(multiplication);
    }

    @Override
    public Expression visit(Division division) throws UndefinedVariableException {
        return visitBinaryExpression(division);
    }

    @Override
    public Expression visit(Num num) {
        return num;
    }

    @Override
    public Expression visit(Equality equality) throws UndefinedVariableException {
        equality.right = equality.right.evaluate(this);
        context.setValue(equality.left, equality.right);
        return equality.right;
    }

    private Expression visitBinaryExpression(MathBinaryOperation binaryExpression) throws UndefinedVariableException {
        binaryExpression.left = binaryExpression.left.evaluate(this);
        binaryExpression.right = binaryExpression.right.evaluate(this);
        return binaryExpression.simplify();
    }

}
