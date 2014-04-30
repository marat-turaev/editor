package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.operators.*;

public class Evaluator implements ExpressionVisitor {
    private Context context;

    public Evaluator(Context context) {
        this.context = context;
    }

    @Override
    public Expression visit(Num num) {
        return num;
    }

    @Override
    public Expression visit(Identifier identifier) {
        if (context.hasValue(identifier)) {
            return new Num(context.getValue(identifier));
        }
        return identifier;
    }

    @Override
    public Expression visit(Equality equality) {
        equality.right = equality.right.accept(this);
        if (equality.right instanceof Num) {
            Number value = ((Num) equality.right).number;
            context.setValue(equality.left, value);
            return equality.right;
        }
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
