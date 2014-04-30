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
        sum.left = sum.left.accept(this);
        sum.right = sum.right.accept(this);
        return sum.simplify();
    }

    @Override
    public Expression visit(Sub sub) {
        sub.left = sub.left.accept(this);
        sub.right = sub.right.accept(this);
        return sub.simplify();
    }

    @Override
    public Expression visit(Multiply multiply) {
        multiply.left = multiply.left.accept(this);
        multiply.right = multiply.right.accept(this);
        return multiply.simplify();
    }

    @Override
    public Expression visit(Div div) {
        div.left = div.left.accept(this);
        div.right = div.right.accept(this);
        return div.simplify();
    }
}
