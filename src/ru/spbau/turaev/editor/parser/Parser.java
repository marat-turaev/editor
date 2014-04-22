package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.CollectionExtensions;
import ru.spbau.turaev.editor.common.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Parser<T> {
    public abstract Collection<Pair<T, String>> parse(String input);

    /**
     * Monad :: return
     */
    public static <T> Parser<T> returnM(T t) {
        return new Parser<T>() {
            @Override
            public Collection<Pair<T, String>> parse(String input) {
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
        return this.bindM(t -> parser);
    }

    /**
     * Monad :: >>=
     */
    <R> Parser<R> bindM(Function<T, Parser<R>> arrow) {
        return new Parser<R>() {
            @Override
            public Collection<Pair<R, String>> parse(String input) {
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
     * MonadPlus :: mzero
     */
    public static <T> Parser<T> zero() {
        return new Parser<T>() {
            @Override
            public Collection<Pair<T, String>> parse(String input) {
                return new ArrayList<>();
            }
        };
    }

    /**
     * MonadPlus :: mplus
     */
    Parser<T> plus(Supplier<Parser<T>> other) {
        return new Parser<T>() {
            @Override
            public Collection<Pair<T, String>> parse(String input) {
                Collection<Pair<T, String>> parsedByThis = Parser.this.parse(input);
                if (parsedByThis.size() != 0) {
                    return parsedByThis;
                }
                return other.get().parse(input);
            }
        };
    }

    Parser<Collection<T>> many1() {
        return this.bindM(t1 -> many().<Collection<T>>bindM(t2 -> returnM(CollectionExtensions.concat(t1, t2))));
    }

    Parser<Collection<T>> many() {
        return many1().plus(() -> returnM(new ArrayList<>()));
    }
}
