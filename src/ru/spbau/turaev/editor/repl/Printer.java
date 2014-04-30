package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.operators.*;

public class Printer implements ExpressionVisitor {
    private StringBuilder output = new StringBuilder();

    @Override
    public Expression visit(Num exp) {
        output.append(exp.number);
        return exp;
    }

    @Override
    public Expression visit(Identifier exp) {
        output.append(exp.name);
        return exp;
    }

    @Override
    public Expression visit(Equality exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" = ");
        exp.right.accept(this);
        output.append(")");
        return exp;
    }

    @Override
    public Expression visit(Div exp) {
        exp.left.accept(this);
        output.append(" / ");
        exp.right.accept(this);
        return exp;
    }

    @Override
    public Expression visit(Multiply exp) {
        exp.left.accept(this);
        output.append(" * ");
        exp.right.accept(this);
        return exp;
    }

    @Override
    public Expression visit(Sum exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" + ");
        exp.right.accept(this);
        output.append(")");
        return exp;
    }

    @Override
    public Expression visit(Sub exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" - ");
        exp.right.accept(this);
        output.append(")");
        return exp;
    }

    public String getResult() {
        return output.toString();
    }
}
