package ru.spbau.turaev.editor.expression;

public interface Exp {
    public void accept(ExpVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Exp> iterator();
}
