package ru.spbau.turaev.editor.expression;

public interface Expression {
    public Expression accept(ExpReworkerVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Expression> iterator();
}
