package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;
import ru.spbau.turaev.editor.expression.operators.Expression;

import java.util.Collection;

public class ParserFacade {
    public String unparsed;

    public Expression parseExpression(String expression) {
        Collection<Pair<Expression, String>> collection = Combinator.expression().parse(expression);

        for (Pair<Expression, String> pair : collection) {
            this.unparsed = pair.second;
            return pair.first;
        }

        this.unparsed = expression;
        return null;
    }
}
