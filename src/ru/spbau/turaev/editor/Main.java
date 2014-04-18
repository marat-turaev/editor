package ru.spbau.turaev.editor;

public class Main {
    public static void main(String[] args) {
        String test1 = "Hello123asd4asd1, asdasd, 123 + 14";

        CollectionExtentions.PrintCollection(Combinators.integer().parse("42 hello"));
        CollectionExtentions.PrintCollection(Combinators.integer().parse("-42 hello"));
        CollectionExtentions.PrintCollection(Combinators.identifier().parse(test1));

        CollectionExtentions.PrintCollection(Combinators.floating().parse("32.66 test"));

        CollectionExtentions.PrintCollection(Combinators.floating().parse("32 test"));

        CollectionExtentions.PrintCollection(Combinators.floating().parse("-32.4123-123 test"));


        CollectionExtentions.PrintCollection(Combinators.parenthesis(Combinators.integer()).parse("(423)asd"));
        CollectionExtentions.PrintCollection(Combinators.parenthesis(Combinators.integer()).parse("(42a)asd"));
    }
}
