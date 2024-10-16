package org.racketsimulator.expression;

import org.racketsimulator.environment.Environment;

public interface Expression {
    Expression evaluate();
    String content();
}
