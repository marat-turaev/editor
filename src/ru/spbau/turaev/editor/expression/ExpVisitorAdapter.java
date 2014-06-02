package ru.spbau.turaev.editor.expression;

import ru.spbau.turaev.editor.expression.operators.*;

public abstract class ExpVisitorAdapter implements ExpressionVisitor {
    @Override
    public void visit(Sub sum) {
    }

    @Override
    public void visit(Identifier identifier) {
    }

    @Override
    public void visit(Equality equality) {
    }

    @Override
    public void visit(Num num) {
    }

    @Override
    public void visit(Sum sum) {
    }

    @Override
    public void visit(Multiply multiply) {
    }

    @Override
    public void visit(Div div) {
    }
}

//    public static void main(String[] args) {
//        Expression exp = new Sum(
//                new Multiply(
//                        new Sum(new Num(10.0), new Num(21.0)),
//                        new Sum(new Num(22.0), new Num(14.0))
//                ),
//                new Div(
//                        new Sum(new Num(15), new Num(88)),
//                        //new Multiply(new Num(11), new Num(18))
//                        new Num(0)
//                )
//        );
//
//        exp.traverse(new ExpVisitorAdapter() {
//            @Override
//            public void visit(Div div) {
//                div.right.evaluate(new ExpVisitorAdapter() {
//                    @Override
//                    public void visit(Num num) {
//                        if (num.number.intValue() == 0) {
//                            throw new RuntimeException("StaticCheck failed: Divide by zero");
//                        }
//                    }
//                });
//            }
//        });
//
//        exp.evaluate(new PrettyPrinter());
//
////        Iterator<Expression> i = exp.iterator();
////        while (i.hasNext()) {
//        // TODO: implement
////        }
//    }

