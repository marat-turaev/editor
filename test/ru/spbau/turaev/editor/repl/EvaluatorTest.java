package ru.spbau.turaev.editor.repl;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.turaev.editor.expression.operators.*;
import ru.spbau.turaev.editor.repl.visitors.Evaluator;
import ru.spbau.turaev.editor.repl.visitors.Printer;

public class EvaluatorTest {
    @Test
    public void canEvaluateSimpleMathExpressions() throws Exception, UndefinedVariableException {
        Expression expression = new Addition(
                new Multiplication(
                        new Addition(new Num(45), new Num(-5)),
                        new Addition(new Num(1), new Num(4))
                ),
                new Division(
                        new Addition(new Num(15), new Num(35)),
                        new Multiplication(new Num(5), new Num(2))
                )
        );

        SimpleContext simpleContext = new SimpleContext();
        Expression simplified = expression.evaluate(new Evaluator(simpleContext));
        Assert.assertEquals("205.0", Printer.printExpression(simplified));
    }

    @Test
    public void canEvaluateMathExpressionsWithVariables() throws Exception, UndefinedVariableException {
        Expression expression =
                new Equality(
                        new Identifier("x"),
                        new Addition(
                                new Multiplication(
                                        new Addition(new Num(45), new Num(-5)),
                                        new Addition(new Num(1), new Num(4))
                                ),
                                new Division(
                                        new Equality(new Identifier("y"), new Num(35)),
                                        new Multiplication(new Num(5), new Num(2))
                                )
                        )
                );

        SimpleContext context = new SimpleContext();
        Expression simplified = expression.evaluate(new Evaluator(context));
        Assert.assertEquals("203.5", Printer.printExpression(simplified));
        Assert.assertEquals(new Num(35), context.getValue(new Identifier("y")));
        Assert.assertEquals(new Num(203.5), context.getValue(new Identifier("x")));
    }

    @Test(expected = UndefinedVariableException.class)
    public void canEvaluateMathExpressionsWithUndefinedVariables() throws Exception, UndefinedVariableException {
        // x = y + 1
        Expression expression =
                new Equality(
                        new Identifier("x"),
                        new Addition(
                                new Identifier("y"),
                                new Num(1)
                        )
                );

        expression.evaluate(new Evaluator(new SimpleContext()));
    }

    @Test
    public void canEvaluateMultipleExpressions() throws UndefinedVariableException {
        SimpleContext context = new SimpleContext();

        // x = 1
        Expression expression1 = new Equality(new Identifier("x"), new Num(1));
        Expression simplified = expression1.evaluate(new Evaluator(context));

        Assert.assertEquals("1", Printer.printExpression(simplified));
        Assert.assertEquals(new Num(1), context.getValue(new Identifier("x")));

        // x = x + 1
        Expression expression2 = new Equality(
                new Identifier("x"),
                new Addition(
                        new Identifier("x"),
                        new Num(1)
                )
        );
        simplified = expression2.evaluate(new Evaluator(context));

        Assert.assertEquals("2.0", Printer.printExpression(simplified));
        Assert.assertEquals(new Num(2.0), context.getValue(new Identifier("x")));
    }
}