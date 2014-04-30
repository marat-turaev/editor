package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.parser.ParserFacade;
import ru.spbau.turaev.editor.repl.Printer;

public class Main {
    public static void main(String[] args) {
        ParserFacade facade = new ParserFacade();
        Expression expression = facade.parseExpression("x = 1 + 1 * x -  89");
        Printer printer = new Printer();
        expression.accept(printer);
        System.out.print(printer.getResult());
        System.out.println(";" + facade.unparsed);
        System.out.println("Done");
    }
}