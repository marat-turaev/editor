package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.Identifier;

public interface Context {
    void setValue(Identifier identifier, Number value);
    Number getValue(Identifier identifier);
}
