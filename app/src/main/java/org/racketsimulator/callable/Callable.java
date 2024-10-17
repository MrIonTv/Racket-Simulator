package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;

import java.util.List;
import java.util.Optional;

public interface Callable {
    Expression execute(Optional<List<Expression>> args);
}
