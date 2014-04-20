package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.common.CollectionExtentions;
import ru.spbau.turaev.editor.common.Pair;
import ru.spbau.turaev.editor.expression.*;
import ru.spbau.turaev.editor.parser.*;

public class Main {
    public static void main(String[] args) {
        String test1 = "Hello123asd4asd1, asdasd, 123 + 14";


        CollectionExtentions.PrintCollection(Combinator.parenthesised(Combinator.integer()).parse("42"));
        CollectionExtentions.PrintCollection(Combinator.spaced(Combinator.integer()).parse("42"));
        CollectionExtentions.PrintCollection(Combinator.spaced(Combinator.integer()).parse("42 "));
        CollectionExtentions.PrintCollection(Combinator.spaced(Combinator.integer()).parse("  42 "));
        CollectionExtentions.PrintCollection(Combinator.parenthesised(Combinator.integer()).parse("42)"));
        CollectionExtentions.PrintCollection(Combinator.parenthesised(Combinator.integer()).parse("(42)"));

        CollectionExtentions.PrintCollection(Combinator.integer().parse("42 hello"));
        CollectionExtentions.PrintCollection(Combinator.integer().parse("-42 hello"));
        CollectionExtentions.PrintCollection(Combinator.identifier().parse(test1));

        CollectionExtentions.PrintCollection(Combinator.floating().parse("32.66 test"));
        CollectionExtentions.PrintCollection(Combinator.floating().parse("32 test"));
        CollectionExtentions.PrintCollection(Combinator.floating().parse("-32.4123-123 test"));

        CollectionExtentions.PrintCollection(Combinator.floatNum().parse("-32.4123-123 test"));
        CollectionExtentions.PrintCollection(Combinator.integerNum().parse("-32.4123-123 test"));
        CollectionExtentions.PrintCollection(Combinator.parenthesised(Combinator.num()).parse("-32.4123-123 test"));
        CollectionExtentions.PrintCollection(Combinator.num().parse("-32.4123-123 test"));

        CollectionExtentions.PrintCollection(Combinator.parenthesised(Combinator.integer()).parse("(423)asd"));
        CollectionExtentions.PrintCollection(Combinator.parenthesised(Combinator.integer()).parse("(42a)asd"));


        CollectionExtentions.PrintCollection(Combinator.spaced(Combinator.integer()).parse("   42   aasd"));

        Exp exp = new Sum(
                new Mul(
                        new Sum(new Num(10.0), new Num(21.0)),
                        new Sum(new Num(22.0), new Num(14.0))
                ),
                new Div(
                        new Sum(new Num(15), new Num(88)),
                        new Num(0)
                )
        );

        exp.accept(new PrettyPrinter());
        System.out.println();

//        String test2 = "(10.0 + 21.0) * (22.0 + 14.0) + (15 + 88) / 0";

        String test2 = "(15 + 88) + (10.0 + 21.0) * (22.0 + 14.0)/ 0";

        for (Pair<Exp, String> p : Combinator.expression().parse(test2)) {
            p.first.accept(new PrettyPrinter());
            System.out.println(";" + p.second);
        }

        System.out.println("Done");
    }
}
