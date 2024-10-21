package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.QExpression;
import org.racketsimulator.expressionbuilder.ExpressionBuilder;

import java.util.List;

public class Eval extends BuiltinCallable {
    private final ExpressionBuilder builder;

    public Eval(Environment runtime, ExpressionBuilder builder) {
        super(runtime);
        this.builder = builder;
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 1)
            throw new InvalidCallableArgs("Operation eval requires exactly one argument.");

        Expression arg = args.getFirst();
        if (!(arg instanceof QExpression) && !(arg instanceof Numeric))
            throw new InvalidCallableArgs("Operation eval requires numeric or QExpression as its arg.");

        String expression = arg.content();
        if (arg instanceof QExpression)
            expression = expression.substring(1);
        Expression repairedExpression = builder.build(expression);

        return repairedExpression.evaluate();
    }
}
