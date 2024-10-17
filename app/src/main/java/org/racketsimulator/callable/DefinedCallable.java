package org.racketsimulator.callable;

import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.QExpression;
import org.racketsimulator.expression.Symbol;

import java.util.List;

public class DefinedCallable implements Callable{
    private final String body;

    public DefinedCallable(String body) {
        this.body = body;
    }

    /**
     * @param args Not used.
     * @return The body of the callable.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (!args.isEmpty())
            throw new InvalidCallableArgs("DefinedCallable requires exactly 0 args");

        final List<Expression> body = List.of(new Symbol(this.body));
        return new QExpression(body);
    }
}
