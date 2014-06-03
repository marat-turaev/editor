package ru.spbau.turaev.editor.repl.visitors;

import ru.spbau.turaev.editor.expression.ExpressionVisitor;
import ru.spbau.turaev.editor.expression.operators.*;
import ru.spbau.turaev.editor.repl.Context;

import java.util.ArrayList;
import java.util.List;

public class Colorizer implements ExpressionVisitor {
    private final Context context;
    private final boolean simplifyMode;
    private int currentPosition = 0;
    private List<Segment> segmentList = new ArrayList<>();

    public Colorizer(Context context, boolean simplifyMode) {
        this.context = context;
        this.simplifyMode = simplifyMode;
    }

    @Override
    public void visit(Num num) {
        int from = currentPosition;
        int to = currentPosition += num.number.toString().length();
        segmentList.add(new Segment(from, to, "operand"));
    }

    public List<Segment> getSegments() {
        return segmentList;
    }

    @Override
    public void visit(Identifier identifier) {
        String style = "operand";
        if (!context.hasValue(identifier) && !simplifyMode) {
            style = "error";
        }

        int from = currentPosition;
        int to = currentPosition += identifier.name.length();
        segmentList.add(new Segment(from, to, style));
    }

    @Override
    public void visit(Addition addition) {
        currentPosition++;
        addition.left.accept(this);
        currentPosition += 3;
        addition.right.accept(this);
        currentPosition++;
    }

    @Override
    public void visit(Multiplication mul) {
        mul.left.accept(this);
        currentPosition += 3;
        mul.right.accept(this);
    }

    @Override
    public void visit(Division division) {
        division.left.accept(this);
        currentPosition += 3;
        division.right.accept(this);
    }

    @Override
    public void visit(Equality equality) {
        currentPosition++;
        equality.left.accept(this);
        currentPosition += 3;
        equality.right.accept(this);
        currentPosition++;
    }

    @Override
    public void visit(Subtraction subtraction) {
        currentPosition++;
        subtraction.left.accept(this);
        currentPosition += 3;
        subtraction.right.accept(this);
        currentPosition++;
    }

    public class Segment {
        private final int begin;
        private final int end;
        private final String name;

        private Segment(int begin, int end, String name) {
            this.begin = begin;
            this.end = end;
            this.name = name;
        }

        public int getBegin() {
            return begin;
        }

        public int getEnd() {
            return end;
        }

        public String getStyle() {
            return name;
        }

        public int length() {
            return end - begin;
        }
    }
}