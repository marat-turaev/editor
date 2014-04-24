package ru.spbau.turaev.editor.expression;

public interface Expression {
    public void accept(ExpVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Expression> iterator();

    public Expression simplify();
}
