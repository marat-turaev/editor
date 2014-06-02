package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.expression.operators.Identifier;

public interface Context {
    void setValue(Identifier identifier, Expression value);
    boolean hasValue(Identifier identifier);
    Expression getValue(Identifier identifier);
}
