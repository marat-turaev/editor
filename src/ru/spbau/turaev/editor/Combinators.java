package ru.spbau.turaev.editor;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class Combinators {
    private static Parser<Character> item() {
        return new Parser<Character>() {
            @Override
            Collection<Pair<Character, String>> parse(String input) {
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

    static Parser<Character> character(char c) {
        return sat(p -> p == c);
    }

    static Parser<Character> letter() {
        return sat(Character::isLetter);
    }

    static Parser<Character> digit() {
        return sat(Character::isDigit);
    }

    static Parser<Character> trivia() {
        return sat(Character::isWhitespace);
    }

    static Parser<String> identifier() {
        return letter().bindM(t1 -> letter().plus(digit()).many().bindM(t2 -> {
            Collection<Character> concatenated = CollectionExtentions.concat(t1, t2);
            String result = CollectionExtentions.ConvertToString(concatenated);
            return Parser.returnM(result);
        }));
    }

    static Parser<Integer> integer() {
        return character('-').seq(natural()).bindM(t1 -> Parser.returnM(-1 * t1)).plus(natural());
    }

    static Parser<Double> floating() {
        return integer().bindM(t1 -> character('.').seq(natural()).bindM(t2 -> Parser.returnM(Double.parseDouble(t1.toString() + "." + t2.toString()))));
    }

    static Parser<Integer> natural() {
        return digit().many1().bindM(digits -> Parser.returnM(Integer.parseInt(CollectionExtentions.ConvertToString(digits))));
    }

    private static <B, T> Parser<T> bracket(Parser<B> openBracket, Parser<T> parser, Parser<B> closeBracket) {
        return openBracket.bindM(t1 -> parser.bindM(t2 -> closeBracket.bindM(t3 -> Parser.returnM(t2))));
    }

    static <T> Parser<T> parenthesis(Parser<T> parser) {
        return bracket(character('('), parser, character(')'));
    }

    static Parser<Num> expression() {
        throw new NotImplementedException();
    }

    private static Parser<Num> floatNum() {
        return floating().bindM(t -> Parser.returnM(new Num(t)));
    }

    private static Parser<Num> integerNum() {
        return integer().bindM(t -> Parser.returnM(new Num(t)));
    }

    static Parser<Num> num() {
        return floatNum().plus(integerNum());
    }

    static Parser<Sum> sum() {
        return expression().bindM(t1 -> character('+').seq(expression().bindM(t2 -> Parser.returnM(new Sum(t1, t2)))));
    }

    static Parser<Sub> sub() {
        return expression().bindM(t1 -> character('-').seq(expression().bindM(t2 -> Parser.returnM(new Sub(t1, t2)))));
    }

    static Parser<Mul> mul() {
        return expression().bindM(t1 -> character('*').seq(expression().bindM(t2 -> Parser.returnM(new Mul(t1, t2)))));
    }

    static Parser<Div> div() {
        return expression().bindM(t1 -> character('/').seq(expression().bindM(t2 -> Parser.returnM(new Div(t1, t2)))));
    }
}
