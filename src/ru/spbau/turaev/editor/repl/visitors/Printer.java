package ru.spbau.turaev.editor.repl.visitors;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.operators.*;

public class Printer implements ExpressionVisitor {
    public static String printExpression(Expression expression) {
        if (expression == null) {
            return "";
        }
        Printer printer = new Printer();
        expression.accept(printer);
        return printer.getResult();
    }

    private StringBuilder output = new StringBuilder();

    @Override
    public void visit(Num exp) {
        output.append(exp.number);
    }

    @Override
    public void visit(Identifier exp) {
        output.append(exp.name);
    }

    @Override
    public void visit(Equality exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" = ");
        exp.right.accept(this);
        output.append(")");
    }

    @Override
    public void visit(Division exp) {
        exp.left.accept(this);
        output.append(" / ");
        exp.right.accept(this);
    }

    @Override
    public void visit(Multiplication exp) {
        exp.left.accept(this);
        output.append(" * ");
        exp.right.accept(this);
    }

    @Override
    public void visit(Addition exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" + ");
        exp.right.accept(this);
        output.append(")");
    }

    @Override
    public void visit(Subtraction subtraction) {
        output.append("(");
        subtraction.left.accept(this);
        output.append(" - ");
        subtraction.right.accept(this);
        output.append(")");
    }

    public String getResult() {
        return output.toString();
    }
}
