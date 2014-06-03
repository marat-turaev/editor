package ru.spbau.turaev.editor.parser;

public class ParsingException extends Throwable {
    public String unparsed;

    public ParsingException(String unparsed) {
        super(String.format("Can't parse substring: %s", unparsed));
        this.unparsed = unparsed;
    }
}
