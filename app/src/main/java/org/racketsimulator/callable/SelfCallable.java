package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;

import java.util.List;

public class SelfCallable implements Callable{
    private final Expression selfExpression;

    public SelfCallable(Expression selfExpression) {
        this.selfExpression = selfExpression;
    }

    /**
     * @param args not used.
     * @return used to return Symbols, Empties and Numerics.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (!args.isEmpty())
            throw new InvalidCallableArgs("SelfCallable requires exactly 0 args");

        return selfExpression;
    }
}
