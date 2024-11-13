package org.racketsimulator.callable.builtin;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.List;

public class DefinedCallable extends BuiltinCallable {
    private final List<Expression> body;

    public DefinedCallable(Environment runtime, List<Expression> body) {
        super(runtime);
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

        return new SExpression(body, super.runtime);
    }

    @Override
    protected Expression validateQExpression(QExpression expression) {
        return null;
    }
}
