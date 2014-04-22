package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;
import ru.spbau.turaev.editor.expression.Exp;

import java.util.Collection;

public class ParserFacade {
    public String unparsed;

    public Exp parseExpression(String expression) {
        Collection<Pair<Exp, String>> collection = Combinator.expression().parse(expression);

        for (Pair<Exp, String> pair : collection) {
            this.unparsed = pair.second;
            return pair.first;
        }

        this.unparsed = expression;
        return null;
    }
}
