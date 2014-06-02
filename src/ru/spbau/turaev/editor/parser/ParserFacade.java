package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;
import ru.spbau.turaev.editor.expression.operators.Expression;

import java.util.Collection;

public class ParserFacade {
    public static String unparsed;

    public static Expression parseExpression(String expression) {
        Collection<Pair<Expression, String>> collection = Combinator.expression().parse(expression);

        for (Pair<Expression, String> pair : collection) {
            unparsed = pair.second;
            return pair.first;
        }

        unparsed = expression;
        return null;
    }
}
