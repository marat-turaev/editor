package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.common.CollectionExtensions;
import ru.spbau.turaev.editor.expression.*;
import ru.spbau.turaev.editor.parser.Combinator;
import ru.spbau.turaev.editor.parser.CombinatorBlocks;
import ru.spbau.turaev.editor.parser.ParserFacade;

public class Main {
    public static void main(String[] args) {
        String test1 = "Hello123asd4asd1, asdasd, 123 + 14";


        CollectionExtensions.PrintCollection(CombinatorBlocks.parenthesised(Combinator.integer()).parse("42"));
        CollectionExtensions.PrintCollection(CombinatorBlocks.spaced(Combinator.integer()).parse("42"));
        CollectionExtensions.PrintCollection(CombinatorBlocks.spaced(Combinator.integer()).parse("42 "));
        CollectionExtensions.PrintCollection(CombinatorBlocks.spaced(Combinator.integer()).parse("  42 "));
        CollectionExtensions.PrintCollection(CombinatorBlocks.parenthesised(Combinator.integer()).parse("42)"));
        CollectionExtensions.PrintCollection(CombinatorBlocks.parenthesised(Combinator.integer()).parse("(42)"));

        CollectionExtensions.PrintCollection(Combinator.integer().parse("42 hello"));
        CollectionExtensions.PrintCollection(Combinator.integer().parse("-42 hello"));
        CollectionExtensions.PrintCollection(Combinator.identifier().parse(test1));

        CollectionExtensions.PrintCollection(CombinatorBlocks.letter().parse("32.66 test"));
        CollectionExtensions.PrintCollection(Combinator.floating().parse("32 test"));
        CollectionExtensions.PrintCollection(Combinator.floating().parse("-32.4123-123 test"));

        CollectionExtensions.PrintCollection(Combinator.floatNum().parse("-32.4123-123 test"));
        CollectionExtensions.PrintCollection(Combinator.integerNum().parse("-32.4123-123 test"));
        CollectionExtensions.PrintCollection(CombinatorBlocks.parenthesised(Combinator.num()).parse("-32.4123-123 test"));
        CollectionExtensions.PrintCollection(Combinator.num().parse("-32.4123-123 test"));

        CollectionExtensions.PrintCollection(CombinatorBlocks.parenthesised(Combinator.integer()).parse("(423)asd"));
        CollectionExtensions.PrintCollection(CombinatorBlocks.parenthesised(Combinator.integer()).parse("(42a)asd"));


        CollectionExtensions.PrintCollection(CombinatorBlocks.spaced(Combinator.integer()).parse("   42   aasd"));

        Equality equality = new Equality(new Identifier("x"), new Sum(new Num(4), new Num(5)));


        String test2 = "x = 1 + 1 * x -  89";

        ParserFacade facade = new ParserFacade();
        Exp exp = facade.parseExpression(test2);
        exp.accept(new PrettyPrinter());
        System.out.println(";" + facade.unparsed);

        System.out.println("Done");
    }
}
