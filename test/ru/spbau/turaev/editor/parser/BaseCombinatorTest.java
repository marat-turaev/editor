package ru.spbau.turaev.editor.parser;

import ru.spbau.turaev.editor.common.Pair;

import java.util.Collection;

/**
 * Created by marat on 22/04/14.
 */
public class BaseCombinatorTest {
    protected <T> Pair<T, String> getFirst(Collection<Pair<T, String>> collection) {
        for (Pair<T, String> pair : collection) {
            return pair;
        }
        return null;
    }
}
