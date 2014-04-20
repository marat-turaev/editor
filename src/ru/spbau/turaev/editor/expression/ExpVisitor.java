package ru.spbau.turaev.editor.expression;

public interface ExpVisitor {
    void visit(Num num);

    void visit(Sum sum);

    void visit(Sub sum);

    void visit(Mul mul);

    void visit(Div div);
}
