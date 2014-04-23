package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.CollectionExtensions;
import ru.spbau.turaev.editor.expression.*;

public class Combinator {
    static Parser<Exp> identifier() {
        return CombinatorBlocks.letter().bindM(t1 -> CombinatorBlocks.letter().plus(CombinatorBlocks::digit).many().bindM(t2 -> {
            String result = CollectionExtensions.ConvertToString(CollectionExtensions.concat(t1, t2));
            return Parser.returnM(new Identifier(result));
        }));
    }

    static Parser<Integer> integer() {
        return CombinatorBlocks.character('-').seq(natural()).bindM(t1 -> Parser.returnM(-1 * t1)).plus(Combinator::natural);
    }

    //TODO: Convert tint.int to float more efficiently
    static Parser<Double> floating() {
        return integer().bindM(t1 -> CombinatorBlocks.character('.').seq(natural()).bindM(t2 -> Parser.returnM(Double.parseDouble(t1.toString() + "." + t2.toString()))));
    }

    private static Parser<Integer> natural() {
        return CombinatorBlocks.digit().many1().bindM(digits -> Parser.returnM(Integer.parseInt(CollectionExtensions.ConvertToString(digits))));
    }

    static Parser<Exp> expression() {
        return sum().plus(Combinator::sub).plus(Combinator::term);
    }

    private static Parser<Exp> term() {
        return div().plus(Combinator::mul).plus(Combinator::primary);
    }

    private static Parser<Exp> primary() {
        return num().plus(() -> equality()).plus(() -> identifier()).plus(() -> CombinatorBlocks.parenthesised(expression()));
    }

    static Parser<Exp> floatNum() {
        return floating().bindM(t -> Parser.returnM(new Num(t)));
    }

    static Parser<Exp> integerNum() {
        return integer().bindM(t -> Parser.returnM(new Num(t)));
    }

    static Parser<Exp> num() {
        return floatNum().plus(Combinator::integerNum);
    }

    static Parser<Exp> sum() {
        return term().bindM(t1 -> CombinatorBlocks.sumToken().seq(expression().bindM(t2 -> Parser.returnM(new Sum(t1, t2)))));
    }

    static Parser<Exp> sub() {
        return term().bindM(t1 -> CombinatorBlocks.subToken().seq(expression().bindM(t2 -> Parser.returnM(new Sub(t1, t2)))));
    }

    static Parser<Exp> mul() {
        return primary().bindM(t1 -> CombinatorBlocks.mulToken().seq(term().bindM(t2 -> Parser.returnM(new Mul(t1, t2)))));
    }

    static Parser<Exp> div() {
        return primary().bindM(t1 -> CombinatorBlocks.divToken().seq(term().bindM(t2 -> Parser.returnM(new Div(t1, t2)))));
    }

    public static Parser<Exp> equality() {
        return identifier().bindM(t1 -> CombinatorBlocks.equalityToken().seq(expression().bindM(t2 -> Parser.returnM(new Equality(t1, t2)))));
    }
}
