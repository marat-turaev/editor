package ru.spbau.turaev.editor.expression;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionTest {
    private String printExpression(Expression expression) {
        StringPrinter printer = new StringPrinter();
        expression.accept(printer);
        return printer.getResult();
    }

    @Test
    public void canSimplifySimpleMathExpressions() throws Exception {
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
        Expression simplified = expression.accept(new Simplifier());
        Assert.assertEquals("205.0", printExpression(simplified));
    }

    @Test
    public void canSimplifySimpleMathExpressionsWithVariables() throws Exception {
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
        Expression simplified = expression.accept(new Simplifier());
        Assert.assertEquals("(x = (200.0 + (y = 35) / 10.0))", printExpression(simplified));
    }

    @Test
    public void canSimplify() throws Exception {
        Expression expression = new Sub(
                new Multiply(new Num(2), new Sum(new Num(3), new Num(2))),
                new Multiply(new Num(4), new Sum(new Num(3), new Identifier("x")))
        );
        Expression simplified = expression.accept(new Simplifier());
        Assert.assertEquals("(10.0 - 4 * (3 + x))", printExpression(simplified));
    }
}