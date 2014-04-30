package ru.spbau.turaev.editor.repl;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.turaev.editor.expression.operators.*;

public class EvaluatorTest {
    private String printExpression(Expression expression) {
        Printer printer = new Printer();
        expression.accept(printer);
        return printer.getResult();
    }

    @Test
    public void canEvaluateSimpleMathExpressions() throws Exception {
        Expression expression = new Sum(
                new Multiply(
                        new Sum(new Num(45), new Num(-5)),
                        new Sum(new Num(1), new Num(4))
                ),
                new Div(
                        new Sum(new Num(15), new Num(35)),
                        new Multiply(new Num(5), new Num(2))
                )
        );

        SimpleContext simpleContext = new SimpleContext();
        Expression simplified = expression.accept(new Evaluator(simpleContext));
        Assert.assertEquals("205.0", printExpression(simplified));
    }

    @Test
    public void canEvaluateMathExpressionsWithVariables() throws Exception {
        Expression expression =
                new Equality(
                        new Identifier("x"),
                        new Sum(
                                new Multiply(
                                        new Sum(new Num(45), new Num(-5)),
                                        new Sum(new Num(1), new Num(4))
                                ),
                                new Div(
                                        new Equality(new Identifier("y"), new Num(35)),
                                        new Multiply(new Num(5), new Num(2))
                                )
                        )
                );

        SimpleContext context = new SimpleContext();
        Expression simplified = expression.accept(new Evaluator(context));
        Assert.assertEquals("203.5", printExpression(simplified));
        Assert.assertEquals(35, context.getValue(new Identifier("y")));
        Assert.assertEquals(203.5, context.getValue(new Identifier("x")));
    }

    @Test
    public void canEvaluateMathExpressionsWithUndefinedVariables() throws Exception {
        Expression expression =
                new Equality(
                        new Identifier("x"),
                        new Sum(
                                new Identifier("y"),
                                new Num(1)
                        )
                );

        SimpleContext context = new SimpleContext();
        Expression simplified = expression.accept(new Evaluator(context));
        Assert.assertEquals("(x = (y + 1))", printExpression(simplified));
        Assert.assertFalse(context.hasValue(new Identifier("y")));
        Assert.assertFalse(context.hasValue(new Identifier("x")));
    }

    @Test
    public void canEvaluateMultipleExpressions() {
        SimpleContext context = new SimpleContext();

        Expression expression1 = new Equality(new Identifier("x"), new Num(1));
        Expression simplified = expression1.accept(new Evaluator(context));

        Assert.assertEquals("1", printExpression(simplified));
        Assert.assertEquals(1, context.getValue(new Identifier("x")));

        Expression expression2 = new Equality(
                new Identifier("x"),
                new Sum(
                        new Identifier("x"),
                        new Num(1)
                )
        );
        simplified = expression2.accept(new Evaluator(context));

        Assert.assertEquals("2.0", printExpression(simplified));
        Assert.assertEquals(2.0, context.getValue(new Identifier("x")));
    }
}