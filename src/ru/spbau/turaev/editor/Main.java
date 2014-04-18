package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.common.CollectionExtentions;
import ru.spbau.turaev.editor.common.Pair;

import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        String test1 = "Hello123asd4asd1, asdasd, 123 + 14";

        CollectionExtentions.PrintCollection(Combinators.integer().parse("42 hello"));
        CollectionExtentions.PrintCollection(Combinators.integer().parse("-42 hello"));
        CollectionExtentions.PrintCollection(Combinators.identifier().parse(test1));

        CollectionExtentions.PrintCollection(Combinators.floating().parse("32.66 test"));
        CollectionExtentions.PrintCollection(Combinators.floating().parse("32 test"));
        CollectionExtentions.PrintCollection(Combinators.floating().parse("-32.4123-123 test"));

        CollectionExtentions.PrintCollection(Combinators.parenthesised(Combinators.integer()).parse("(423)asd"));
        CollectionExtentions.PrintCollection(Combinators.parenthesised(Combinators.integer()).parse("(42a)asd"));


        CollectionExtentions.PrintCollection(Combinators.spaced(Combinators.integer()).parse("   42   aasd"));

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

        String test2 = "1+2";

        Collection<Pair<Exp, String>> parsed = Combinators.expression().parse(test2);

        System.out.println("Done");
    }
}
