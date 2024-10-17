package org.racketsimulator.expression;

public interface Expression {
    Expression evaluate();
    String content();
    int valueSize();
}
