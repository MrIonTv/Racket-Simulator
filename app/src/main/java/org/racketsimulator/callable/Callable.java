package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;

import java.util.List;

public interface Callable {
    Expression execute(List<Expression> args);
}
