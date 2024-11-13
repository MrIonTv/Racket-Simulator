package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.List;

public class Car extends BuiltinCallable {
    public Car(Environment runtime) {
        super(runtime);
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 1)
            throw new InvalidCallableArgs("Operation car requires exactly one argument.");

        Expression arg = args.getFirst();
        arg = arg.evaluate();
        if (!(arg instanceof Pair))
            throw new InvalidCallableArgs("Operation car requires a List as it's argument.");

        List<Expression> list = arg.content();
        if (!list.isEmpty())
            return new QExpression(list.getFirst());

        throw new InvalidCallableArgs("Operation car can't handle an empty List");
    }

    @Override
    protected Expression validateQExpression(QExpression expression) {
        throw new RuntimeException("Operation car is not using.");
    }
}
