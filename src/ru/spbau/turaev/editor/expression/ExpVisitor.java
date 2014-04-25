package ru.spbau.turaev.editor.expression;

public interface ExpVisitor {
    void visit(Num num);

    void visit(Equality equality);

    void visit(Identifier identifier);

    void visit(Sum sum);

    void visit(Sub sum);

    void visit(Multiply multiply);

    void visit(Div div);
}


