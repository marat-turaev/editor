package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.expression.*;
import ru.spbau.turaev.editor.parser.ParserFacade;

public class Main {
    public static void main(String[] args) {
        Equality equality = new Equality(new Identifier("x"), new Sum(new Num(4), new Num(5)));


        String test2 = "x = 1 + 1 * x -  89";

        ParserFacade facade = new ParserFacade();
        Exp exp = facade.parseExpression(test2);
        exp.accept(new PrettyPrinter());
        System.out.println(";" + facade.unparsed);

        System.out.println("Done");
    }
}
