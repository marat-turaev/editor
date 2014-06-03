package ru.spbau.turaev.editor.expression.operators;

import ru.spbau.turaev.editor.expression.EvaluatorVisitor;
import ru.spbau.turaev.editor.expression.ExpressionVisitor;

public class Num implements Expression {
    public final Number number;

    public Num(Number number) {
        this.number = number;
    }

    @Override
    public Expression evaluate(EvaluatorVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Num num = (Num) o;

        if (number != null ? !number.equals(num.number) : num.number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
