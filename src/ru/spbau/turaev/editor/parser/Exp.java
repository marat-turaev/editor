package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.ExpVisitor;

public interface Exp {
    public void accept(ExpVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Exp> iterator();
}
