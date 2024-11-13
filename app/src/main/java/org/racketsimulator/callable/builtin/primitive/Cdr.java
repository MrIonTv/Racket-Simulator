package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Empty;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Pair;
import org.racketsimulator.expression.QExpression;

import java.util.List;

public class Cdr extends BuiltinCallable {
    public Cdr(Environment runtime) {
        super(runtime);
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 1)
            throw new InvalidCallableArgs("Operation cdr requires exactly one argument.");

        Expression arg = args.getFirst();
        arg = arg.evaluate();
        if (!(arg instanceof Pair))
            throw new InvalidCallableArgs("Operation cdr requires a List as it's argument.");

        if (arg.valueSize() < 2)
            return new QExpression(new Empty());

        List<Expression> cdr = arg.content().subList(1, arg.valueSize());

        return new Pair(cdr);
    }

    @Override
    protected Expression validateQExpression(QExpression expression) {
        throw new RuntimeException("Operation cdr is not using.");
    }
}
