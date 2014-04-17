package ru.spbau.turaev.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%s;%s)", first.toString(), second.toString());
    }
}

abstract class Parser<T> {
    abstract Collection<Pair<T, String>> parse(String input);

    static <T> Parser<T> returnM(T t) {
        return new Parser<T>() {
            @Override
            Collection<Pair<T, String>> parse(String input) {
                ArrayList<Pair<T, String>> result = new ArrayList<>();
                result.add(new Pair<>(t, input));
                return result;
            }
        };
    }

    <R> Parser<R> bindM(Function<T, Parser<R>> arrow) {
        return new Parser<R>() {
            @Override
            Collection<Pair<R, String>> parse(String input) {
                Collection<Pair<R, String>> result = new ArrayList<>();
                for (Pair<T, String> p : Parser.this.parse(input)) {
                    T t = p.first;
                    String inp = p.second;
                    Collection<Pair<R, String>> chunk = arrow.apply(t).parse(inp);
                    result.addAll(chunk);
                }
                return result;
            }
        };
    }

    Parser<T> plus(Parser<T> other) {
        return new Parser<T>() {
            @Override
            Collection<Pair<T, String>> parse(String input) {
                Collection<Pair<T, String>> parsedByThis = Parser.this.parse(input);
                Collection<Pair<T, String>> parsedByOther = other.parse(input);
                ArrayList<Pair<T, String>> result = new ArrayList<>();
                result.addAll(parsedByThis);
                result.addAll(parsedByOther);
                return result;
            }
        };
    }

    Parser<Collection<T>> many() {
        return this.bindM(t -> {
            return this.many().<Collection<T>>bindM(t1 -> {
                Collection<T> result = new ArrayList<>();
                result.add(t);
                result.addAll(t1);
                return returnM(result);
            });
        }).plus(returnM(new ArrayList<>()));
    }

    static <T> Parser<T> zero() {
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
                return returnM(c);
            } else {
                return zero();
            }
        });
    }

    static Parser<Character> alpha() {
        return lower().plus(upper());
    }

    static Parser<Character> lower() {
        return sat(Character::isLowerCase);
    }

    static Parser<Character> upper() {
        return sat(Character::isUpperCase);
    }

    static Parser<Character> digit() {
        return sat(Character::isDigit);
    }

    static Parser<Collection<Character>> word() {
        return alpha().many();
    }

    static Parser<String> identifier() {
        return alpha().bindM(t1 -> lower().plus(upper()).plus(digit()).many().bindM(t2 -> {
            StringBuilder result = new StringBuilder(1 + t2.size());
            result.append(t1);
            for (Character c : t2) {
                result.append(c);
            }
            return returnM(result.toString());
        }));
    }

    private static Parser<String> word2() {
        return neWord2().plus(returnM(""));
    }

    private static Parser<String> neWord2() {
        return alpha().bindM(x -> word2().bindM(xs -> returnM(x + xs)));
    }
}

public class Main {
    public static <T> void PrintCollection(Collection<T> collection) {
        System.out.print("[");
        for (T item : collection) {
            System.out.print(item.toString());
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        String test1 = "Hello123, {}";

        PrintCollection(Parser.word().parse(test1));
        PrintCollection(Parser.digit().many().parse(test1));
        PrintCollection(Parser.identifier().parse(test1));
    }
}
