package ru.spbau.turaev.editor.expression;

public class PrettyPrinter implements ExpVisitor {
    @Override
    public void visit(Num exp) {
        System.out.print(exp.number);
    }

    @Override
    public void visit(Identifier exp) {
        System.out.print(exp.name);
    }

    @Override
    public void visit(Equality exp) {
        System.out.print("(");
        exp.left.accept(this);
        System.out.print(" = ");
        exp.right.accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(Div exp) {
        exp.left.accept(this);
        System.out.print(" / ");
        exp.right.accept(this);
    }

    @Override
    public void visit(Multiply exp) {
        exp.left.accept(this);
        System.out.print(" * ");
        exp.right.accept(this);
    }

    @Override
    public void visit(Sum exp) {
        System.out.print("(");
        exp.left.accept(this);
        System.out.print(" + ");
        exp.right.accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(Sub exp) {
        System.out.print("(");
        exp.left.accept(this);
        System.out.print(" - ");
        exp.right.accept(this);
        System.out.print(")");
    }
}


