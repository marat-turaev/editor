package ru.spbau.turaev.editor.expression;

public abstract class ExpVisitorAdapter implements ExpVisitor {
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
    public void visit(Mul mul) {
    }

    @Override
    public void visit(Div div) {
    }
}

//    public static void main(String[] args) {
//        Exp exp = new Sum(
//                new Mul(
//                        new Sum(new Num(10.0), new Num(21.0)),
//                        new Sum(new Num(22.0), new Num(14.0))
//                ),
//                new Div(
//                        new Sum(new Num(15), new Num(88)),
//                        //new Mul(new Num(11), new Num(18))
//                        new Num(0)
//                )
//        );
//
//        exp.traverse(new ExpVisitorAdapter() {
//            @Override
//            public void visit(Div div) {
//                div.right.accept(new ExpVisitorAdapter() {
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
//        exp.accept(new PrettyPrinter());
//
////        Iterator<Exp> i = exp.iterator();
////        while (i.hasNext()) {
//        // TODO: implement
////        }
//    }

