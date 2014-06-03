package ru.spbau.turaev.editor.repl;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.turaev.editor.expression.operators.*;
import ru.spbau.turaev.editor.repl.visitors.Printer;
import ru.spbau.turaev.editor.repl.visitors.Simplifier;

public class SimplifierTest {
    @Test
    public void canSimplifySimpleMathExpressions() throws Exception, UndefinedVariableException {
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
        Expression simplified = expression.evaluate(new Simplifier(new SimpleContext()));
        Assert.assertEquals("205.0", Printer.printExpression(simplified));
    }

    @Test
    public void canSimplifySimpleMathExpressionsWithVariables() throws Exception, UndefinedVariableException {
        Expression expression =
                new Equality(
                        new Identifier("x"),
                        new Addition(
                                new Multiplication(
                                        new Addition(new Num(45), new Num(-5)),
                                        new Addition(new Num(1), new Num(4))
                                ),
                                new Division(
                                        new Division(new Identifier("y"), new Num(35)),
                                        new Multiplication(new Num(5), new Num(2))
                                )
                        )
                );
        Expression simplified = expression.evaluate(new Simplifier(new SimpleContext()));
        Assert.assertEquals("(200.0 + y / 35 / 10.0)", Printer.printExpression(simplified));
    }

    @Test
    public void canSimplify() throws Exception, UndefinedVariableException {
        Expression expression = new Subtraction(
                new Multiplication(new Num(2), new Addition(new Num(3), new Num(2))),
                new Multiplication(new Num(4), new Addition(new Num(3), new Identifier("x")))
        );
        Expression simplified = expression.evaluate(new Simplifier(new SimpleContext()));
        Assert.assertEquals("(10.0 - 4 * (3 + x))", Printer.printExpression(simplified));
    }
}