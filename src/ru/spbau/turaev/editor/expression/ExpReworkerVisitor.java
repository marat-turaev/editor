package ru.spbau.turaev.editor.expression;

public interface ExpReworkerVisitor {
    Expression visit(Num exp);

    Expression visit(Identifier identifier);

    Expression visit(Equality equality);

    Expression visit(Sum sum);

    Expression visit(Sub sum);

    Expression visit(Multiply multiply);

    Expression visit(Div div);
}
