package ru.spbau.turaev.editor.expression;

import ru.spbau.turaev.editor.expression.operators.*;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public interface EvaluatorVisitor {
    Expression visit(Num exp);

    Expression visit(Identifier identifier) throws UndefinedVariableException;

    Expression visit(Equality equality) throws UndefinedVariableException;

    Expression visit(Sum sum) throws UndefinedVariableException;

    Expression visit(Sub sub) throws UndefinedVariableException;

    Expression visit(Multiply multiply) throws UndefinedVariableException;

    Expression visit(Div div) throws UndefinedVariableException;
}
