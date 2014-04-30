package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.operators.*;

public class Simplifier extends ReplVisitor {
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
}

