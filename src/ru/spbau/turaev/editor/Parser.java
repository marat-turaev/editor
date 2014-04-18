package ru.spbau.turaev.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

abstract class Parser<T> {
    abstract Collection<Pair<T, String>> parse(String input);

    /**
     * MonadPlus :: mzero
     */
    public static <T> Parser<T> zero() {
        return new Parser<T>() {
            @Override
            Collection<Pair<T, String>> parse(String input) {
                return new ArrayList<>();
            }
        };
    }

    /**
     * Monad :: return
     */
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

    /**
     * Monad :: >>
     */
    <R> Parser<R> seq(Parser<R> parser) {
        return this.bindM((t) -> parser);
    }

    /**
     * Monad :: >>=
     */
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

    /**
     * MonadPlus :: mplus
     */
    Parser<T> plus(Parser<T> other) {
        return new Parser<T>() {
            @Override
            Collection<Pair<T, String>> parse(String input) {
                Collection<Pair<T, String>> parsedByThis = Parser.this.parse(input);
                Collection<Pair<T, String>> parsedByOther = other.parse(input);
                return CollectionExtentions.concat(parsedByThis, parsedByOther);
            }
        };
    }

    Parser<Collection<T>> many1() {
        return this.bindM(t -> many().<Collection<T>>bindM(t1 -> returnM(CollectionExtentions.concat(t, t1))));
    }

    Parser<Collection<T>> many() {
        return many1().plus(returnM(new ArrayList<>()));
    }
}
