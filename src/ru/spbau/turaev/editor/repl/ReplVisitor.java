package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.operators.*;

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
    public Expression visit(Sum sum) throws UndefinedVariableException {
        return visitBinaryExpression(sum);
    }

    @Override
    public Expression visit(Sub sub) throws UndefinedVariableException {
        return visitBinaryExpression(sub);
    }

    @Override
    public Expression visit(Multiply multiply) throws UndefinedVariableException {
        return visitBinaryExpression(multiply);
    }

    @Override
    public Expression visit(Div div) throws UndefinedVariableException {
        return visitBinaryExpression(div);
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
