package ru.spbau.turaev.editor.repl;

public class UndefinedVariableException extends Throwable {
    public UndefinedVariableException(String variable) {
        super(variable + " is undefined.");
    }
}
