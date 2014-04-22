package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Created by marat on 22/04/14.
 */
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

    public static Parser<Character> sumToken() {
        return Combinator.spaced(character('+'));
    }

    public static Parser<Character> subToken() {
        return Combinator.spaced(character('-'));
    }

    public static Parser<Character> mulToken() {
        return Combinator.spaced(character('*'));
    }

    public static Parser<Character> divToken() {
        return Combinator.spaced(character('/'));
    }

    public static Parser<Character> openParenthesisToken() {
        return Combinator.spaced(character('('));
    }

    public static Parser<Character> closeParenthesisToken() {
        return Combinator.spaced(character(')'));
    }
}
