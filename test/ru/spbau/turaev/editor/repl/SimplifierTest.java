package ru.spbau.turaev.editor.repl;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.turaev.editor.expression.operators.*;

public class SimplifierTest {
    private String printExpression(Expression expression) {
        Printer printer = new Printer();
        expression.accept(printer);
        return printer.getResult();
    }

    @Test
    public void canSimplifySimpleMathExpressions() throws Exception, UndefinedVariableException {
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
        Expression simplified = expression.evaluate(new Simplifier(new SimpleContext()));
        Assert.assertEquals("205.0", printExpression(simplified));
    }

    @Test
    public void canSimplifySimpleMathExpressionsWithVariables() throws Exception, UndefinedVariableException {
        Expression expression =
                new Equality(
                        new Identifier("x"),
                        new Sum(
                                new Multiply(
                                        new Sum(new Num(45), new Num(-5)),
                                        new Sum(new Num(1), new Num(4))
                                ),
                                new Div(
                                        new Div(new Identifier("y"), new Num(35)),
                                        new Multiply(new Num(5), new Num(2))
                                )
                        )
                );
        Expression simplified = expression.evaluate(new Simplifier(new SimpleContext()));
        Assert.assertEquals("(200.0 + y / 35 / 10.0)", printExpression(simplified));
    }

    @Test
    public void canSimplify() throws Exception, UndefinedVariableException {
        Expression expression = new Sub(
                new Multiply(new Num(2), new Sum(new Num(3), new Num(2))),
                new Multiply(new Num(4), new Sum(new Num(3), new Identifier("x")))
        );
        Expression simplified = expression.evaluate(new Simplifier(new SimpleContext()));
        Assert.assertEquals("(10.0 - 4 * (3 + x))", printExpression(simplified));
    }
}