package org.racketsimulator.expressionbuilder;

import org.racketsimulator.expression.Expression;

public interface ExpressionBuilder {
    Expression build(String sentence);
}
