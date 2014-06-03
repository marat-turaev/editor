package ru.spbau.turaev.editor.actions;

import ru.spbau.turaev.editor.repl.Context;

public class EvaluationUndo {
    private final String lastResult;
    private final Context context;

    public EvaluationUndo(String userInput, Context context) {
        this.lastResult = userInput;
        this.context = context.copy();
    }

    public String getLastResult() {
        return lastResult;
    }

    public Context getContext() {
        return context;
    }
}
