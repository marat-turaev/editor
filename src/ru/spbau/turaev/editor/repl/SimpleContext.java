package ru.spbau.turaev.editor.repl;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.expression.operators.Identifier;

import java.util.HashMap;

public class SimpleContext implements Context {
    private HashMap<String, Expression> values = new HashMap<>();

    @Override
    public void setValue(Identifier identifier, Expression value) {
        values.put(identifier.name, value);
    }

    @Override
    public boolean hasValue(Identifier identifier) {
        return values.containsKey(identifier.name);
    }

    @Override
    public Expression getValue(Identifier identifier) {
        if (values.containsKey(identifier.name)) {
            return values.get(identifier.name);
        }
        return null;
    }
}
