package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.CollectionExtentions;
import ru.spbau.turaev.editor.expression.*;

import java.util.Collection;


//TODO: Review access modifiers
public class Combinator {
    public static Parser<String> identifier() {
        return CombinatorBlocks.letter().bindM(t1 -> CombinatorBlocks.letter().plus(CombinatorBlocks::digit).many().bindM(t2 -> {
            Collection<Character> concatenated = CollectionExtentions.concat(t1, t2);
            String result = CollectionExtentions.ConvertToString(concatenated);
            return Parser.returnM(result);
        }));
    }

    public static Parser<Integer> integer() {
        return CombinatorBlocks.character('-').seq(natural()).bindM(t1 -> Parser.returnM(-1 * t1)).plus(Combinator::natural);
    }

    //TODO: Convert tint.int to float more efficiently
    public static Parser<Double> floating() {
        return integer().bindM(t1 -> CombinatorBlocks.character('.').seq(natural()).bindM(t2 -> Parser.returnM(Double.parseDouble(t1.toString() + "." + t2.toString()))));
    }

    private static Parser<Integer> natural() {
        return CombinatorBlocks.digit().many1().bindM(digits -> Parser.returnM(Integer.parseInt(CollectionExtentions.ConvertToString(digits))));
    }

    private static <B, T> Parser<T> ignoreSurrounded(Parser<B> prefix, Parser<T> parser, Parser<B> suffix) {
        return prefix.seq(parser.bindM(t2 -> suffix.seq(Parser.returnM(t2))));
    }

    public static <T> Parser<T> parenthesised(Parser<T> parser) {
        return ignoreSurrounded(CombinatorBlocks.openParenthesisToken(), parser, CombinatorBlocks.closeParenthesisToken());
    }

    public static <T> Parser<T> spaced(Parser<T> parser) {
        return ignoreSurrounded(CombinatorBlocks.trivia().many(), parser, CombinatorBlocks.trivia().many());
    }

    public static Parser<Exp> expression() {
        return sum().plus(Combinator::sub).plus(Combinator::term);
    }

    private static Parser<Exp> term() {
        return div().plus(Combinator::mul).plus(Combinator::primary);
    }

    private static Parser<Exp> primary() {
        return num().plus(() -> parenthesised(expression()));
    }

    public static Parser<Exp> floatNum() {
        return floating().bindM(t -> Parser.returnM(new Num(t)));
    }

    public static Parser<Exp> integerNum() {
        return integer().bindM(t -> Parser.returnM(new Num(t)));
    }

    public static Parser<Exp> num() {
        return floatNum().plus(Combinator::integerNum);
    }

    public static Parser<Exp> sum() {
        return term().bindM(t1 -> CombinatorBlocks.sumToken().seq(expression().bindM(t2 -> Parser.returnM(new Sum(t1, t2)))));
    }

    public static Parser<Exp> sub() {
        return term().bindM(t1 -> CombinatorBlocks.subToken().seq(expression().bindM(t2 -> Parser.returnM(new Sub(t1, t2)))));
    }

    public static Parser<Exp> mul() {
        return primary().bindM(t1 -> CombinatorBlocks.mulToken().seq(term().bindM(t2 -> Parser.returnM(new Mul(t1, t2)))));
    }

    public static Parser<Exp> div() {
        return primary().bindM(t1 -> CombinatorBlocks.divToken().seq(term().bindM(t2 -> Parser.returnM(new Div(t1, t2)))));
    }
}
