package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.expression.*;
import ru.spbau.turaev.editor.parser.ParserFacade;

public class Main {
    public static void main(String[] args) {
        ParserFacade facade = new ParserFacade();
        Expression expression = facade.parseExpression("x = 1 + 1 * x -  89");
        StringPrinter printer = new StringPrinter();
        expression.accept(printer);
        System.out.print(printer.getResult());
        System.out.println(";" + facade.unparsed);
        System.out.println("Done");
    }
}