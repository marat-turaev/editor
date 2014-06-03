package ru.spbau.turaev.editor;

import ru.spbau.turaev.editor.actions.AllowInputOnlyToLastLineFilter;
import ru.spbau.turaev.editor.actions.UserInputProcessor;
import ru.spbau.turaev.editor.actions.Utilities;
import ru.spbau.turaev.editor.expression.operators.Expression;
import ru.spbau.turaev.editor.parser.ParserFacade;
import ru.spbau.turaev.editor.parser.ParsingException;
import ru.spbau.turaev.editor.repl.visitors.Colorizer;
import ru.spbau.turaev.editor.repl.visitors.Printer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class REPLConsole {
    public static final String SIMPLIFY = "Simplify";
    public static final String EVALUATE = "Evaluate";
    public static final String GREETING = System.lineSeparator() + ">";

    private UserInputProcessor userInputProcessor;
    private UndoableEditListener undoableEditListener;

    public static void main(String[] args) {
        REPLConsole replConsole = new REPLConsole();
        replConsole.init();
    }

    private void init() {

        undoableEditListener = e -> userInputProcessor.saveUndoableEdit(e.getEdit());

        JFrame frame = new JFrame();
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
        optionPane.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userInputProcessor.setMode(SIMPLIFY.equals(optionPane.getSelectedItem()));
            }
        });
        frame.add(optionPane, "North");

        StyledDocument styledDocument = new DefaultStyledDocument();
        Style base = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style def = styledDocument.addStyle("default", base);
        StyleConstants.setForeground(def, Color.BLACK);

        Style operand = styledDocument.addStyle("operand", base);
        StyleConstants.setForeground(operand, Color.GREEN);
        StyleConstants.setBold(operand, true);

        Style error = styledDocument.addStyle("error", base);
        StyleConstants.setForeground(error, Color.RED);
        StyleConstants.setBold(error, true);

        Style parseError = styledDocument.addStyle("parseError", base);
        StyleConstants.setBackground(parseError, Color.RED);

        JTextPane textArea = new JTextPane(styledDocument);

        textArea.setText("Welcome to REPL Console! " + System.lineSeparator() + ">");
        textArea.setEditable(true);
        frame.add(textArea, "Center");

        userInputProcessor = new UserInputProcessor();
        optionPane.setSelectedIndex(0);

        AbstractDocument document = (AbstractDocument) textArea.getDocument();
        // ----------- add listeners ----------
        document.setDocumentFilter(new AllowInputOnlyToLastLineFilter());
        document.addUndoableEditListener(undoableEditListener);
        document.addDocumentListener(new Coloring(textArea));

        textArea.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke("ENTER"), new EvaluateAction());
        textArea.getKeymap().addActionForKeyStroke(KeyStroke.getKeyStroke("control shift Z"), new UndoEvaluationAction());

        textArea.getKeymap().addActionForKeyStroke(
                KeyStroke.getKeyStroke("control Z"),
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        userInputProcessor.undoEdit();
                    }
                }
        );

        frame.setVisible(true);
        frame.setSize(400, 1200);
    }

    private abstract class PrintToConsoleAction extends AbstractAction {
        protected Document document;

        protected abstract String getStringToPrint() throws BadLocationException;

        @Override
        public void actionPerformed(ActionEvent e) {
            JEditorPane source = (JEditorPane) e.getSource();
            document = source.getDocument();
            try {
                document.removeUndoableEditListener(undoableEditListener);

                String result = getStringToPrint();
                if (result != null) {
                    document.insertString(Utilities.endOffset(document), System.lineSeparator() + result, null);
                    document.insertString(Utilities.endOffset(document), GREETING, null);
                    source.setCaretPosition(Utilities.endOffset(document));
                }
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            } finally {
                document.addUndoableEditListener(undoableEditListener);
            }
        }
    }

    private class Coloring implements DocumentListener {
        private JTextPane textArea;

        public Coloring(JTextPane textArea) {
            this.textArea = textArea;
        }

        public void insertUpdate(DocumentEvent e) {
            process(e);
        }

        public void removeUpdate(DocumentEvent e) {
            process(e);
        }

        public void changedUpdate(DocumentEvent e) {
        }

        public void process(DocumentEvent e) {
            final StyledDocument document = (StyledDocument) e.getDocument();
            try {
                String text = document.getText(0, document.getLength());
                boolean isUserInput = '>' == text.charAt(Utilities.lastLineIndex(document) + 1);
                int begin = Utilities.lastLineIndex(document) + GREETING.length();
                final String userInput = text.substring(begin);
                if (!isUserInput || userInput.isEmpty()) {
                    return;
                }
                try {
                    Expression exp = ParserFacade.parseExpression(userInput);
                    String unparsed = ParserFacade.unparsed;
                    SwingUtilities.invokeLater(() -> {
                        document.removeUndoableEditListener(undoableEditListener);
                        document.removeDocumentListener(this);
                        try {
                            document.remove(begin, userInput.length());
                            document.insertString(Utilities.endOffset(document), Printer.printExpression(exp) + unparsed, null);
                            textArea.setCaretPosition(Utilities.endOffset(document));
                        } catch (BadLocationException e1) {
                            e1.printStackTrace();
                        }
                        document.addDocumentListener(this);
                        document.addUndoableEditListener(undoableEditListener);
                    });

                    if (!unparsed.equals("")) {
                        throw new ParsingException(unparsed);
                    }

                    Colorizer c = new Colorizer(userInputProcessor.getContext(), userInputProcessor.isSimplifyMode());
                    exp.accept(c);
                    SwingUtilities.invokeLater(() -> {
                        document.removeUndoableEditListener(undoableEditListener);
                        document.setCharacterAttributes(begin, userInput.length(), document.getStyle("default"), true);
                        for (Colorizer.Segment segment : c.getSegments()) {
                            document.setCharacterAttributes(begin + segment.getBegin(), segment.length(), document.getStyle(segment.getStyle()), true);
                        }
                        document.addUndoableEditListener(undoableEditListener);
                    });

                } catch (ParsingException e1) {
                    SwingUtilities.invokeLater(() -> {
                        document.removeUndoableEditListener(undoableEditListener);
                        document.setCharacterAttributes(begin + userInput.length() - e1.unparsed.length(), e1.unparsed.length(), document.getStyle("parseError"), true);
                        document.addUndoableEditListener(undoableEditListener);
                    });
                }
            } catch (BadLocationException ignored) {
            }
        }
    }

    public class UndoEvaluationAction extends PrintToConsoleAction {
        @Override
        protected String getStringToPrint() throws BadLocationException {
            return userInputProcessor.undoEvaluation();
        }
    }

    public class EvaluateAction extends PrintToConsoleAction {
        @Override
        protected String getStringToPrint() throws BadLocationException {
            String userInput = document.getText(0, document.getLength()).substring(Utilities.lastLineIndex(document) + GREETING.length());
            if (userInput.isEmpty())
                return null;
            return userInputProcessor.parseAndEvaluate(userInput);
        }
    }
}

