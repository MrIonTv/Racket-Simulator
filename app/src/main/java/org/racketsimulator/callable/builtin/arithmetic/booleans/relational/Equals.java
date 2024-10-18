package org.racketsimulator.callable.builtin.arithmetic.booleans.relational;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.Symbol;

import java.util.List;

public class Equals implements Callable {
    /**
     * @param args Numerical Expressions to be evaluated.
     * @return #t or #f Symbol.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (args.isEmpty())
            throw new InvalidCallableArgs("Operator = requires at least one Numeric arg.");

        boolean valueOfTruth = true;
        Expression subject = args.getFirst().evaluate();
        if (!(subject instanceof Numeric))
            throw new InvalidCallableArgs("Operator = requires all of its args to be Numerical. Received: " +
                    subject.content() + ".");

        int subjectInt = Integer.parseInt(subject.content());
        for (Expression arg : args) {
            Expression value = arg.evaluate();
            if (!(value instanceof Numeric))
                throw new InvalidCallableArgs("Operator = requires all of its args to be Numerical. Received: " +
                        value.content() + ".");
            int valueInt = Integer.parseInt(value.content());
            if (subjectInt != valueInt)
                valueOfTruth = false;
        }

        if (valueOfTruth)
            return new Symbol("#t");
        return new Symbol("#f");
    }
}
