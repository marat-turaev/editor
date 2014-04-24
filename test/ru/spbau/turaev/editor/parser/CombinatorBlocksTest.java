package ru.spbau.turaev.editor.parser;

import org.junit.Assert;
import org.junit.Test;
import ru.spbau.turaev.editor.common.Pair;

public class CombinatorBlocksTest extends BaseCombinatorTest {
    @Test
    public void testCharacterPositive() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.character('c').parse("ca"));

        Assert.assertTrue('c' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testCharacterNegative() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.character('c').parse("ac"));

        Assert.assertNull(parseResult);
    }

    @Test
    public void testLetterPositive() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.letter().parse("ca"));

        Assert.assertTrue('c' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testLetterNegative() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.letter().parse("1a"));
        Assert.assertNull(parseResult);
    }

    @Test
    public void testDigitPositive() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.digit().parse("1a"));

        Assert.assertTrue('1' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testDigitNegative() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.digit().parse("ca"));
        Assert.assertNull(parseResult);
    }

    @Test
    public void testSumToken() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.sumToken().parse(" +  a"));

        Assert.assertTrue('+' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testEqualityToken() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.equalityToken().parse(" =  a"));

        Assert.assertTrue('=' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testDivToken() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.divToken().parse(" /  a"));

        Assert.assertTrue('/' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testMulToken() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.mulToken().parse(" *  a"));

        Assert.assertTrue('*' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testSubToken() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.subToken().parse(" -  a"));

        Assert.assertTrue('-' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }

    @Test
    public void testParenthesised() throws Exception {
        Pair<Character, String> parseResult = getFirst(CombinatorBlocks.parenthesised(CombinatorBlocks.character('c')).parse("  \t (  c \t ) \t a"));

        Assert.assertTrue('c' == parseResult.first);
        Assert.assertEquals("a", parseResult.second);
    }
}