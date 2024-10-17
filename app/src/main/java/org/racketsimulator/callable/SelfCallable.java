package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;

import java.util.List;

public class SelfCallable implements Callable{
    private final Expression selfExpression;

    public SelfCallable(Expression selfExpression) {
        this.selfExpression = selfExpression;
    }

    /**
     * @param args
     * @return
     */
    @Override
    public Expression execute(List<Expression> args) {
        return selfExpression;
    }
}
