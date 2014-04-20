package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.CollectionExtentions;
import ru.spbau.turaev.editor.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;


//TODO: Review access modifiers
public class Combinators {
    private static Parser<Character> item() {
        return new Parser<Character>() {
            @Override
            public Collection<Pair<Character, String>> parse(String input) {
                ArrayList<Pair<Character, String>> arrayList = new ArrayList<>();
                if (input.isEmpty()) {
                    return arrayList;
                }
                arrayList.add(new Pair<>(input.charAt(0), input.substring(1)));
                return arrayList;
            }
        };
    }

    private static Parser<Character> sat(Predicate<Character> predicate) {
        return item().bindM(c -> {
            if (predicate.test(c)) {
                return Parser.returnM(c);
            } else {
                return Parser.zero();
            }
        });
    }

    public static Parser<Character> character(char c) {
        return sat(p -> p == c);
    }

    public static Parser<Character> letter() {
        return sat(Character::isLetter);
    }

    public static Parser<Character> digit() {
        return sat(Character::isDigit);
    }

    public static Parser<Character> trivia() {
        return sat(Character::isWhitespace);
    }

    public static Parser<String> identifier() {
        return letter().bindM(t1 -> letter().plus(Combinators::digit).many().bindM(t2 -> {
            Collection<Character> concatenated = CollectionExtentions.concat(t1, t2);
            String result = CollectionExtentions.ConvertToString(concatenated);
            return Parser.returnM(result);
        }));
    }

    public static Parser<Integer> integer() {
        return character('-').seq(natural()).bindM(t1 -> Parser.returnM(-1 * t1)).plus(Combinators::natural);
    }

    public static Parser<Double> floating() {
        return integer().bindM(t1 -> character('.').seq(natural()).bindM(t2 -> Parser.returnM(Double.parseDouble(t1.toString() + "." + t2.toString()))));
    }

    public static Parser<Integer> natural() {
        return digit().many1().bindM(digits -> Parser.returnM(Integer.parseInt(CollectionExtentions.ConvertToString(digits))));
    }

    private static <B, T> Parser<T> ignoreSurrounded(Parser<B> prefix, Parser<T> parser, Parser<B> suffix) {
        return prefix.bindM(t1 -> parser.bindM(t2 -> suffix.bindM(t3 -> Parser.returnM(t2))));
    }

    public static <T> Parser<T> parenthesised(Parser<T> parser) {
        return ignoreSurrounded(openParenthesisToken(), parser, closeParenthesisToken());
    }

    public static <T> Parser<T> maybeParenthesised(Parser<T> parser) {
        return parenthesised(parser).plus(() -> parser);
    }

    public static <T> Parser<T> spaced(Parser<T> parser) {
        return ignoreSurrounded(trivia().many(), parser, trivia().many());
    }

    public static Parser<Character> sumToken() {
        return spaced(character('+'));
    }

    public static Parser<Character> subToken() {
        return spaced(character('-'));
    }

    public static Parser<Character> mulToken() {
        return spaced(character('*'));
    }

    public static Parser<Character> divToken() {
        return spaced(character('/'));
    }

    public static Parser<Character> openParenthesisToken() {
        return spaced(character('('));
    }

    public static Parser<Character> closeParenthesisToken() {
        return spaced(character(')'));
    }

    public static Parser<Exp> expression() {
        return maybeParenthesised(mul())
                .plus(() -> maybeParenthesised(div()))
                .plus(() -> maybeParenthesised(sum()))
                .plus(() -> maybeParenthesised(sub()))
                .plus(() -> maybeParenthesised(factor()));
    }

    public static Parser<Exp> parenthesisedExpression() {
        return parenthesised(mul())
                .plus(() -> parenthesised(div()))
                .plus(() -> parenthesised(sum()))
                .plus(() -> parenthesised(sub()))
                .plus(() -> parenthesised(factor()));
    }

    public static Parser<Exp> floatNum() {
        return floating().bindM(t -> Parser.returnM(new Num(t)));
    }

    public static Parser<Exp> integerNum() {
        return integer().bindM(t -> Parser.returnM(new Num(t)));
    }

    static Parser<Exp> factor() {
        return num().plus(Combinators::parenthesisedExpression);
    }

    public static Parser<Exp> num() {
        return floatNum().plus(Combinators::integerNum);
    }

    static Parser<Exp> sum() {
        return factor().bindM(t1 -> sumToken().seq(expression().bindM(t2 -> Parser.returnM(new Sum(t1, t2)))));
    }

    static Parser<Exp> sub() {
        return factor().bindM(t1 -> subToken().seq(expression().bindM(t2 -> Parser.returnM(new Sub(t1, t2)))));
    }

    static Parser<Exp> mul() {
        return factor().bindM(t1 -> mulToken().seq(expression().bindM(t2 -> Parser.returnM(new Mul(t1, t2)))));
    }

    static Parser<Exp> div() {
        return factor().bindM(t1 -> divToken().seq(expression().bindM(t2 -> Parser.returnM(new Div(t1, t2)))));
    }
}
