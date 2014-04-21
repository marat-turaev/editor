package ru.spbau.turaev.editor.expression;

public class PrettyPrinter implements ExpVisitor {

    public void visit(Num exp) {
        System.out.print(exp.number);
    }

    public void visit(Div exp) {
        exp.left.accept(this);
        System.out.print(" / ");
        exp.right.accept(this);
    }

    public void visit(Mul exp) {
        exp.left.accept(this);
        System.out.print(" * ");
        exp.right.accept(this);
    }

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
