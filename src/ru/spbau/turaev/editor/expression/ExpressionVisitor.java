package ru.spbau.turaev.editor.expression;

import ru.spbau.turaev.editor.expression.operators.*;

public interface ExpressionVisitor {
    void visit(Num num);

    void visit(Equality equality);

    void visit(Identifier identifier);

    void visit(Addition addition);

    void visit(Subtraction sum);

    void visit(Multiplication multiplication);

    void visit(Division division);
}


