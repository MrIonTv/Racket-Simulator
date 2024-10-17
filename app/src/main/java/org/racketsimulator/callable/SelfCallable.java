package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;

import java.util.List;
import java.util.Optional;

public class SelfCallable implements Callable{
    private final Expression selfExpression;

    public SelfCallable(Expression selfExpression) {
        this.selfExpression = selfExpression;
    }

    /**
     * @param args not used.
     * @return used to return not QExpressions.
     */
    @Override
    public Expression execute(Optional<List<Expression>> args) {
        return selfExpression;
    }
}
