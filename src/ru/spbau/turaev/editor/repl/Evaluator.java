package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.operators.Equality;
import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.expression.operators.Identifier;
import ru.spbau.turaev.editor.expression.operators.Num;

public class Evaluator extends ReplVisitor {
    private Context context;

    public Evaluator(Context context) {
        this.context = context;
    }

    @Override
    public Expression visit(Num num) {
        return num;
    }

    @Override
    public Expression visit(Identifier identifier) {
        if (context.hasValue(identifier)) {
            return new Num(context.getValue(identifier));
        }
        return identifier;
    }

    @Override
    public Expression visit(Equality equality) {
        equality.right = equality.right.accept(this);
        if (equality.right instanceof Num) {
            Number value = ((Num) equality.right).number;
            context.setValue(equality.left, value);
            return equality.right;
        }
        return equality;
    }
}
