package org.racketsimulator.parser;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.QExpression;

public interface Parser {
    Expression build(String sentence);
}
