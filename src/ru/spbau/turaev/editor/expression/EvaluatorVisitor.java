package ru.spbau.turaev.editor.expression;

import ru.spbau.turaev.editor.expression.operators.*;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;

public interface EvaluatorVisitor {
    Expression visit(Num exp);

    Expression visit(Identifier identifier) throws UndefinedVariableException;

    Expression visit(Equality equality) throws UndefinedVariableException;

    Expression visit(Addition addition) throws UndefinedVariableException;

    Expression visit(Subtraction subtraction) throws UndefinedVariableException;

    Expression visit(Multiplication multiplication) throws UndefinedVariableException;

    Expression visit(Division division) throws UndefinedVariableException;
}
