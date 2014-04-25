package ru.spbau.turaev.editor.expression;

public class StringPrinter implements ExpVisitor {
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
    public void visit(Div exp) {
        exp.left.accept(this);
        output.append(" / ");
        exp.right.accept(this);
    }

    @Override
    public void visit(Multiply exp) {
        exp.left.accept(this);
        output.append(" * ");
        exp.right.accept(this);
    }

    @Override
    public void visit(Sum exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" + ");
        exp.right.accept(this);
        output.append(")");
    }

    @Override
    public void visit(Sub exp) {
        output.append("(");
        exp.left.accept(this);
        output.append(" - ");
        exp.right.accept(this);
        output.append(")");
    }

    public String getResult() {
        return output.toString();
    }
}
