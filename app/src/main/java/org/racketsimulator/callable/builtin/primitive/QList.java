package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.ArrayList;
import java.util.List;

public class QList extends BuiltinCallable {
    public QList(Environment runtime) {
        super(runtime);
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.isEmpty())
            return new QExpression(new Empty());
        List<Expression> result = new ArrayList<>();
        for (Expression arg : args) {
            if (arg instanceof SExpression)
                arg = arg.evaluate();
            result.add(arg);
        }
        return new Pair(result);
    }

    @Override
    protected Expression validateQExpression(QExpression expression) {
        throw new RuntimeException("Not implemented for Quote.");
    }
}
