package ru.spbau.turaev.editor.expression;

import ru.spbau.turaev.editor.expression.operators.*;

public interface ExpressionVisitor {
    Expression visit(Num exp);

    Expression visit(Identifier identifier);

    Expression visit(Equality equality);

    Expression visit(Sum sum);

    Expression visit(Sub sub);

    Expression visit(Multiply multiply);

    Expression visit(Div div);
}
