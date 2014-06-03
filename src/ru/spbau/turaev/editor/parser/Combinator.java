package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.CollectionExtensions;
import ru.spbau.turaev.editor.expression.operators.*;

public class Combinator {
    static Parser<Expression> identifier() {
        return CombinatorBlocks.letter().bindM(t1 -> CombinatorBlocks.letter().plus(CombinatorBlocks::digit).many().bindM(t2 -> {
            String result = CollectionExtensions.ConvertToString(CollectionExtensions.concat(t1, t2));
            return Parser.returnM(new Identifier(result));
        }));
    }

    static Parser<Integer> integer() {
        return CombinatorBlocks.character('-').seq(natural()).bindM(t1 -> Parser.returnM(-1 * t1)).plus(Combinator::natural);
    }

    static Parser<Double> floating() {
        return integer().bindM(t1 -> CombinatorBlocks.character('.').seq(natural()).bindM(t2 -> Parser.returnM(Double.parseDouble(t1.toString() + "." + t2.toString()))));
    }

    private static Parser<Integer> natural() {
        return CombinatorBlocks.digit().many1().bindM(digits -> Parser.returnM(Integer.parseInt(CollectionExtensions.ConvertToString(digits))));
    }

    static Parser<Expression> expression() {
        return sum().plus(Combinator::sub).plus(Combinator::term);
    }

    private static Parser<Expression> term() {
        return div().plus(Combinator::mul).plus(() -> CombinatorBlocks.spaced(primary()));
    }

    private static Parser<Expression> primary() {
        return num().plus(Combinator::equality).plus(Combinator::identifier).plus(() -> CombinatorBlocks.parenthesised(expression()));
    }

    static Parser<Expression> floatNum() {
        return floating().bindM(t -> Parser.returnM(new Num(t)));
    }

    static Parser<Expression> integerNum() {
        return integer().bindM(t -> Parser.returnM(new Num(t)));
    }

    static Parser<Expression> num() {
        return floatNum().plus(Combinator::integerNum);
    }

    static Parser<Expression> sum() {
        return term().bindM(t1 -> CombinatorBlocks.sumToken().seq(expression().bindM(t2 -> Parser.returnM(new Addition(t1, t2)))));
    }

    static Parser<Expression> sub() {
        return term().bindM(t1 -> CombinatorBlocks.subToken().seq(expression().bindM(t2 -> Parser.returnM(new Subtraction(t1, t2)))));
    }

    static Parser<Expression> mul() {
        return primary().bindM(t1 -> CombinatorBlocks.mulToken().seq(term().bindM(t2 -> Parser.returnM(new Multiplication(t1, t2)))));
    }

    static Parser<Expression> div() {
        return primary().bindM(t1 -> CombinatorBlocks.divToken().seq(term().bindM(t2 -> Parser.returnM(new Division(t1, t2)))));
    }

    public static Parser<Expression> equality() {
        return identifier().bindM(t1 -> CombinatorBlocks.equalityToken().seq(expression().bindM(t2 -> Parser.returnM(new Equality((Identifier) t1, t2)))));
    }
}
