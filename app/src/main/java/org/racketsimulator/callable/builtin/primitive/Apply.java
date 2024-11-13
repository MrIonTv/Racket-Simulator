package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;

public class Apply extends BuiltinCallable {
    public static final String SPACE = " ";
    public static final String OPEN_SYMBOL = "(";
    public static final String CLOSE_SYMBOL = ")";
    private final ExpressionBuilder builder;

    public Apply(Environment runtime, ExpressionBuilder builder) {
        super(runtime);
        this.builder = builder;
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 2)
            throw new InvalidCallableArgs("Operation apply requires a Symbol and a List as arguments.");

        Expression arg = args.getFirst();
        if (!(arg instanceof Symbol))
            throw new InvalidCallableArgs("Operation apply requires Symbol as its first arg.");

        String expression = OPEN_SYMBOL;

        Expression list = args.getLast();
        if (list instanceof SExpression)
            list = list.evaluate();
        if (!(list instanceof Pair))
            throw new InvalidCallableArgs("Operation apply requires List as its second arg.");

        expression += arg.stringContent();
        String argsString = list.stringContent();
        expression += SPACE;
        expression += argsString.substring(2);
        Expression repairedExpression = builder.build(expression);

        return repairedExpression.evaluate();
    }

    @Override
    protected Expression validateQExpression(QExpression expression) {
        throw new InvalidCallableArgs("Operation apply can't handle QExpression neither List as argument.");
    }
}
