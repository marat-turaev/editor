package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.ExpVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;

public interface Expression {
    public Expression accept(ExpressionVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Expression> iterator();
}
