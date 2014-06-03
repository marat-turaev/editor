package ru.spbau.turaev.editor.actions;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.parser.ParserFacade;
import ru.spbau.turaev.editor.parser.ParsingException;
import ru.spbau.turaev.editor.repl.Context;
import ru.spbau.turaev.editor.repl.SimpleContext;
import ru.spbau.turaev.editor.repl.UndefinedVariableException;
import ru.spbau.turaev.editor.repl.visitors.Evaluator;
import ru.spbau.turaev.editor.repl.visitors.Printer;
import ru.spbau.turaev.editor.repl.visitors.Simplifier;

import javax.swing.undo.UndoableEdit;
import java.util.Stack;

public class UserInputProcessor {
    private final Stack<UndoableEdit> edits = new Stack<>();
    private final Stack<EvaluationAction> evaluations = new Stack<>();

    private boolean simplifyMode;
    private Context context = new SimpleContext();
    private Simplifier simplifier = new Simplifier(context);
    private Evaluator evaluator = new Evaluator(context);

    private String lastResult = null;

    public Context getContext() {
        return context;
    }

    public boolean isSimplifyMode() {
        return simplifyMode;
    }

    public void setMode(boolean simplifyMode) {
        this.simplifyMode = simplifyMode;
    }

    public String parseAndEvaluate(String userInput) {
        edits.clear();
        evaluations.push(new EvaluationAction(lastResult, context));
        String result;
        try {
            Expression expression = ParserFacade.parseExpression(userInput);
            String unparsed = ParserFacade.unparsed;
            if (!unparsed.equals("")) {
                throw new ParsingException(unparsed);
            }
            if (simplifyMode) {
                result = Printer.printExpression(expression.evaluate(simplifier));
            } else {
                result = Printer.printExpression(expression.evaluate(evaluator));
            }
        } catch (UndefinedVariableException | ParsingException e) {
            result = "ERROR: " + e.getMessage();
        }
        lastResult = result;
        return result;
    }

    public void saveUndoableEdit(UndoableEdit edit) {
        if (edit.canUndo())
            edits.push(edit);
    }

    public void undoEdit() {
        if (!edits.empty()) {
            edits.pop().undo();
        }
    }

    public String undoEvaluation() {
        if (!evaluations.empty()) {
            EvaluationAction e = evaluations.pop();
            context = e.getContext();
            lastResult = e.getLastResult();
            return lastResult;
        }
        return null;
    }
}
