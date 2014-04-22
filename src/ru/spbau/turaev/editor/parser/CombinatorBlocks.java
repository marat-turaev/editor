package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class CombinatorBlocks {
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

    private static Parser<Character> satisfy(Predicate<Character> predicate) {
        return item().bindM(c -> {
            if (predicate.test(c)) {
                return Parser.returnM(c);
            } else {
                return Parser.zero();
            }
        });
    }

    public static Parser<Character> character(char c) {
        return satisfy(p -> p == c);
    }

    public static Parser<Character> letter() {
        return satisfy(Character::isLetter);
    }

    public static Parser<Character> digit() {
        return satisfy(Character::isDigit);
    }

    public static Parser<Character> trivia() {
        return satisfy(Character::isWhitespace);
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

    public static Parser<Character> equalityToken() {
        return spaced(character('='));
    }

    public static Parser<Character> openParenthesisToken() {
        return spaced(character('('));
    }

    public static Parser<Character> closeParenthesisToken() {
        return spaced(character(')'));
    }

    private static <B, T> Parser<T> ignoreSurrounded(Parser<B> prefix, Parser<T> parser, Parser<B> suffix) {
        return prefix.seq(parser.bindM(t2 -> suffix.seq(Parser.returnM(t2))));
    }

    public static <T> Parser<T> parenthesised(Parser<T> parser) {
        return ignoreSurrounded(openParenthesisToken(), parser, closeParenthesisToken());
    }
}
