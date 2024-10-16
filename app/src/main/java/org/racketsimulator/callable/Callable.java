package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.QExpression;

import java.util.List;

public interface Callable {
    Expression execute(List<QExpression> qArgs);
}
