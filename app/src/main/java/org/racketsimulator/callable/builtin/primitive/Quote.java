package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.QExpression;

import java.util.List;

public class Quote extends BuiltinCallable {
    public Quote(Environment runtime) {
        super(runtime);
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 1)
            throw new InvalidCallableArgs("Operation quote requires exactly one argument.");
        Expression arg = args.getFirst();
        if (arg instanceof Numeric)
            return arg;
        return new QExpression(List.of(arg));
    }
}
