package org.racketsimulator.callable.builtin.arithmetic.numerical;

import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.QExpression;
import org.racketsimulator.expression.Symbol;

import java.util.List;

public class Quotient extends BuiltinCallable {
    public Quotient(Environment body) {
        super(body);
    }

    /**
     * @param args Arguments that its evaluation must return a Numeric.
     * @return a Numeric of the Sum of all args applied to the first one.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (args.isEmpty())
            throw new InvalidCallableArgs("Operator / requires at least one Numeric.");

        Expression subject = args.getFirst().evaluate();
        if (subject instanceof Symbol)
            subject = fixExpression(subject);

        if (!(subject instanceof Numeric))
            throw new InvalidCallableArgs("Operator / requires all of its args to be Numerical. Received: " +
                    subject.stringContent() + ".");

        int subjectInt = Integer.parseInt(subject.stringContent());

        for (Expression arg : args.subList(1, args.size())) {
            Expression value = arg.evaluate();
            if (value instanceof Symbol)
                value = fixExpression(value);
            if (!(value instanceof Numeric))
                throw new InvalidCallableArgs("Operator / requires all of its args to be Numerical. Received: " +
                        value.stringContent() + ".");
            int valueInt = Integer.parseInt(value.stringContent());
            subjectInt /= valueInt;
        }

        return new Numeric(subjectInt);
    }

    @Override
    protected Expression validateQExpression(QExpression expression) {
        throw new InvalidCallableArgs("Operation / can't handle QExpression neither List as argument.");
    }
}

