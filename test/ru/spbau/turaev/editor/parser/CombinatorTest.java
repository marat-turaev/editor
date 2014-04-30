package ru.spbau.turaev.editor.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.turaev.editor.common.Pair;
import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.repl.Printer;

public class CombinatorTest extends BaseCombinatorTest {
    private String printExpression(Expression expression) {
        Printer printer = new Printer();
        expression.accept(printer);
        return printer.getResult();
    }

    @Test
    public void testIdentifier() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.identifier().parse("a3s3 = 1"));

        Assert.assertEquals("a3s3", printExpression(parseResult.first));
        Assert.assertEquals(" = 1", parseResult.second);
    }

    @Test
    public void testPositiveInteger() throws Exception {
        Pair<Integer, String> parseResult = getFirst(Combinator.integer().parse("42 = b"));

        Assert.assertEquals(42, parseResult.first.intValue());
        Assert.assertEquals(" = b", parseResult.second);
    }

    @Test
    public void testNegativeInteger() throws Exception {
        Pair<Integer, String> parseResult = getFirst(Combinator.integer().parse("-42 = b"));

        Assert.assertEquals(-42, parseResult.first.intValue());
        Assert.assertEquals(" = b", parseResult.second);
    }

    @Test
    public void testFloating() throws Exception {
        Pair<Double, String> parseResult = getFirst(Combinator.floating().parse("-42.52 = b"));

        Assert.assertEquals(-42.52, parseResult.first.doubleValue(), 1e-10);
        Assert.assertEquals(" = b", parseResult.second);
    }

    @Test
    public void testExpression() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.expression().parse("y = 1 + 2 * (-3 / y) + 3 + x = 3 - 2 test"));

        Assert.assertEquals("(y = (1 + (2 * -3 / y + (3 + (x = (3 - 2))))))", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testFloatNum() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.floatNum().parse("-42.42 test"));

        Assert.assertEquals("-42.42", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testIntegerNum() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.integerNum().parse("-42 test"));

        Assert.assertEquals("-42", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testNum() throws Exception {
        Pair<Expression, String> parseResult1 = getFirst(Combinator.num().parse("-42 test"));

        Assert.assertEquals("-42", printExpression(parseResult1.first));
        Assert.assertEquals(" test", parseResult1.second);

        Pair<Expression, String> parseResult2 = getFirst(Combinator.num().parse("-42.42 test"));

        Assert.assertEquals("-42.42", printExpression(parseResult2.first));
        Assert.assertEquals(" test", parseResult2.second);
    }

    @Test
    public void testSum() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.sum().parse("1   +   1 test"));

        Assert.assertEquals("(1 + 1)", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testSub() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.sub().parse("1  -   1 test"));

        Assert.assertEquals("(1 - 1)", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testMul() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.mul().parse("1  * 1 test"));

        Assert.assertEquals("1 * 1", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testDiv() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.div().parse("1  /    1 test"));

        Assert.assertEquals("1 / 1", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }

    @Test
    public void testEquality() throws Exception {
        Pair<Expression, String> parseResult = getFirst(Combinator.equality().parse("x = 1 + 1 test"));

        Assert.assertEquals("(x = (1 + 1))", printExpression(parseResult.first));
        Assert.assertEquals(" test", parseResult.second);
    }
}