package ru.spbau.turaev.editor.actions;

import ru.spbau.turaev.editor.repl.Context;

public class EvaluationAction {
    private final String lastResult;
    private final Context context;

    public EvaluationAction(String userInput, Context context) {
        this.lastResult = userInput;
        this.context = context;
    }

    public String getLastResult() {
        return lastResult;
    }

    public Context getContext() {
        return context;
    }
}
