package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.operators.Identifier;

public interface Context {
    void setValue(Identifier identifier, Number value);
    boolean hasValue(Identifier identifier);
    Number getValue(Identifier identifier);
}
