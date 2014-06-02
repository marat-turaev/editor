package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.parser.ParserFacade;
import ru.spbau.turaev.editor.repl.*;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class REPLConsole {
    private JFrame frame;
    public static final String SIMPLIFY = "Simplify";
    public static final String EVALUATE = "Evaluate";
    public static final String GREETING = System.lineSeparator() + ">";

    private Context context = new SimpleContext();
    private Evaluator evaluator = new Evaluator(context);
    private Simplifier simplifier = new Simplifier(context);

    private void init() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setLayout(new BorderLayout());

        final JComboBox<String> optionPane = new JComboBox<>();
        optionPane.addItem(SIMPLIFY);
        optionPane.addItem(EVALUATE);
        frame.add(optionPane, "North");

        JEditorPane textArea = new JEditorPane();
        AbstractDocument document = (AbstractDocument) textArea.getDocument();
        document.setDocumentFilter(new Filter());
        textArea.setText("Welcome to REPL Console! " + GREETING);
        textArea.setEditable(true);
        frame.add(textArea, "Center");

        textArea.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke("ENTER"), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JEditorPane source = (JEditorPane) e.getSource();
                Document document = source.getDocument();
                try {
                    String text = document.getText(0, document.getLength());
                    String userInput = text.substring(lastLineIndex(document) + GREETING.length());

                    Expression parsedExpression = ParserFacade.parseExpression(userInput);
                    String unparsed = ParserFacade.unparsed;

                    String result = "Result is: " + System.lineSeparator();
                    result += "Parsed: " + Printer.printExpression(parsedExpression) + System.lineSeparator();
                    result += "Unparsed: " + unparsed + System.lineSeparator();
                    result += "Simplified: " + Printer.printExpression(parsedExpression.evaluate(simplifier)) + System.lineSeparator();
                    result += "Evaluated: " + Printer.printExpression(parsedExpression.evaluate(evaluator)) + System.lineSeparator();

                    document.insertString(endOffset(document), System.lineSeparator() + result, null);
                    document.insertString(endOffset(document), GREETING, null);
                    source.setCaretPosition(endOffset(document));
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                } catch (UndefinedVariableException e1) {
                    e1.printStackTrace();
                }
            }

            private int endOffset(Document document) {
                return document.getEndPosition().getOffset() - 1;
            }

            public boolean simplifyMode() {
                return SIMPLIFY.equals(optionPane.getSelectedItem());
            }
        });

        frame.setVisible(true);
        frame.setSize(500, 300);
    }


    public static void main(String[] args) {
        REPLConsole replConsole = new REPLConsole();
        replConsole.init();
    }


    private class Filter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (cursorOnLastLine(offset, fb)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            if (offset > lastLineIndex(fb.getDocument()) + 1) {
                super.remove(fb, offset, length);
            }
        }

        public void replace(final FilterBypass fb, final int offset, final int length, final String text, final AttributeSet attrs)
                throws BadLocationException {
            if (cursorOnLastLine(offset, fb)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

    }

    private static boolean cursorOnLastLine(int offset, DocumentFilter.FilterBypass fb) {
        return cursorOnLastLine(offset, fb.getDocument());
    }

    private static boolean cursorOnLastLine(int offset, Document document) {
        int lastLineIndex = 0;
        try {
            lastLineIndex = lastLineIndex(document);
        } catch (BadLocationException e) {
            return false;
        }
        return offset > lastLineIndex;
    }

    private static int lastLineIndex(Document document) throws BadLocationException {
        return document.getText(0, document.getLength()).lastIndexOf(System.lineSeparator());
    }

}