package org.racketsimulator.expression;

import java.util.List;

public interface Expression {
    Expression evaluate();
    List<Expression> content();
    int valueSize();
    String stringContent();
}