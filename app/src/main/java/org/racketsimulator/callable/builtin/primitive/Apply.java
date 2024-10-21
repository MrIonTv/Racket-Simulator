package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.QExpression;
import org.racketsimulator.expression.Symbol;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;

public class Apply extends BuiltinCallable {
    private final ExpressionBuilder builder;

    public Apply(Environment runtime, ExpressionBuilder builder) {
        super(runtime);
        this.builder = builder;
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 2)
            throw new InvalidCallableArgs("Operation eval requires exactly one argument.");

        Expression arg = args.getFirst();
        if (!(arg instanceof Symbol))
            throw new InvalidCallableArgs("Operation eval requires Symbol as its first arg.");

        String expression = arg.content();

        Expression list = args.getLast();
        if (!(list instanceof QExpression) || list.valueSize() < 2)
            throw new InvalidCallableArgs("Operation eval requires List as its second arg.");

       String argsString = list.content();
       expression += argsString.substring(2);
        Expression repairedExpression = builder.build(expression);

        return repairedExpression.evaluate();
    }
}
