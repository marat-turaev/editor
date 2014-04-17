package ru.spbau.turaev.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class Combinators {
    private static <T> Parser<T> zero() {
        return new Parser<T>() {
            @Override
            Collection<Pair<T, String>> parse(String input) {
                return new ArrayList<>();
            }
        };
    }

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
                return zero();
            }
        });
    }

    static Parser<Character> letter() {
        return sat(Character::isLetter);
    }

    static Parser<Character> digit() {
        return sat(Character::isDigit);
    }

    static Parser<Collection<Character>> word() {
        return letter().many();
    }

    static Parser<Collection<Character>> identifier() {
        return letter().bindM(t1 -> letter().plus(digit()).many().bindM(t2 -> Parser.returnM(CollectionExtentions.concat(t1, t2))));
    }

    static Parser<Integer> integer() {
        return digit().many1().bindM(digits -> {
            char a[] = new char[digits.size()];
            int i = 0;
            for (Character digit : digits) {
                a[i++] = digit;
            }
            return Parser.returnM(Integer.parseInt(String.valueOf(a)));
        });
    }
}
