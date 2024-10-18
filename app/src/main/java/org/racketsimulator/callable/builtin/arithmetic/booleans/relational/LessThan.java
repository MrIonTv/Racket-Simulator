package org.racketsimulator.callable.builtin.arithmetic.booleans.relational;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.Symbol;

import java.util.List;

public class LessThan implements Callable {
    /**
     * @param args Numerical Expressions to be evaluated.
     * @return #t or #f Symbol.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 2)
            throw new InvalidCallableArgs("Operator < requires exactly two Numeric args.");

        boolean valueOfTruth = true;
        Expression subject = args.getFirst().evaluate();
        if (!(subject instanceof Numeric))
            throw new InvalidCallableArgs("Operator < requires all of its args to be Numerical. Received: " +
                    subject.content() + ".");

        Expression tester = args.getLast().evaluate();
        if (!(tester instanceof Numeric))
            throw new InvalidCallableArgs("Operator < requires all of its args to be Numerical. Received: " +
                    tester.content() + ".");

        int subjectInt = Integer.parseInt(subject.content());
        int testerInt = Integer.parseInt(tester.content());
        if (subjectInt >= testerInt)
            valueOfTruth = false;

        if (valueOfTruth)
            return new Symbol("#t");
        return new Symbol("#f");
    }
}