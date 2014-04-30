package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.ExpVisitor;

public interface Expression {
    public Expression accept(ExpressionVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Expression> iterator();
}
