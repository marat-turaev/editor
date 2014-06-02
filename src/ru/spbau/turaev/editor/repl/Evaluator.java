package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.expression.operators.Identifier;

public class Evaluator extends ReplVisitor {
    public Evaluator(Context context) {
        super(context);
    }

    @Override
    public Expression visit(Identifier identifier) throws UndefinedVariableException {
        if (!context.hasValue(identifier)) {
            throw new UndefinedVariableException(identifier.name);
        }
        return super.visit(identifier);
    }
}
