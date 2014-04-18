package ru.spbau.turaev.editor;

interface ExpVisitor {
    void visit(Num num);

    void visit(Sum sum);

    void visit(Sub sum);

    void visit(Mul mul);

    void visit(Div div);
}

abstract class ExpVisitorAdapter implements ExpVisitor {
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

interface Exp {
    public void accept(ExpVisitor visitor);

    public void traverse(ExpVisitor visitor);
//        public Iterator<Exp> iterator();
}

abstract class BiExp implements Exp {
    public final Exp left;
    public final Exp right;

    protected BiExp(Exp left, Exp right) {
        this.left = left;
        this.right = right;
    }
}

class Num implements Exp {
    public final Number number;

    public Num(Number number) {
        this.number = number;
    }

    @Override
    public void accept(ExpVisitor prettyPrinter) {
        prettyPrinter.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        visitor.visit(this);
    }
}

class Sum extends BiExp {
    public Sum(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public void accept(ExpVisitor prettyPrinter) {
        prettyPrinter.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}

class Sub extends BiExp {
    public Sub(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public void accept(ExpVisitor prettyPrinter) {
        prettyPrinter.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}

class Mul extends BiExp {
    public Mul(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public void accept(ExpVisitor prettyPrinter) {
        prettyPrinter.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}

class Div extends BiExp {
    public Div(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public void accept(ExpVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void traverse(ExpVisitor visitor) {
        left.traverse(visitor);
        visitor.visit(this);
        right.traverse(visitor);
    }
}

class PrettyPrinter implements ExpVisitor {

    public void visit(Num exp) {
        System.out.print(exp.number);
    }

    public void visit(Div exp) {
        Div div = exp;
        div.left.accept(this);
        System.out.print(" / ");
        div.right.accept(this);
    }

    public void visit(Mul exp) {
        Mul mul = exp;
        mul.left.accept(this);
        System.out.print(" * ");
        mul.right.accept(this);
    }

    public void visit(Sum exp) {
        Sum sum = exp;
        System.out.print("(");
        sum.left.accept(this);
        System.out.print(" + ");
        sum.right.accept(this);
        System.out.print(")");
    }

    @Override
    public void visit(Sub exp) {
        Sub sum = exp;
        System.out.print("(");
        sum.left.accept(this);
        System.out.print(" - ");
        sum.right.accept(this);
        System.out.print(")");
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

