package org.racketsimulator.callable.builtin.arithmetic.booleans.logical;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.InvalidExpression;
import org.racketsimulator.expression.SExpression;
import org.racketsimulator.expression.Symbol;

import java.util.List;
import java.util.Objects;

public class Not implements Callable {
    /**
     * @param args Requires exactly one Symbol of type #t or #f.
     * @return a Symbol of type #t or #f.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 1)
            throw new InvalidCallableArgs("For NOT operation is required exactly one Symbol of type #t or #f.");

        boolean valueOfTruth = true;
        Expression arg = args.getFirst();
        if (arg instanceof SExpression) {
            var pivot = arg.evaluate();
            if (pivot instanceof Symbol) {
                if (!Objects.equals(pivot.content(), "#f") && !Objects.equals(pivot.content(), "#t"))
                    throw new InvalidExpression("Expecting only #t or #f symbols for NOT operation.");
                if (Objects.equals(pivot.content(), "#t"))
                    valueOfTruth = false;
            } else {
                throw new InvalidExpression("Expecting only #t or #f symbols for NOT operation.");
            }
        } else if (arg instanceof Symbol) {
            if (!Objects.equals(arg.content(), "#f") && !Objects.equals(arg.content(), "#t"))
                throw new InvalidExpression("Expecting only #t or #f symbols for NOT operation.");
            if (Objects.equals(arg.content(), "#t"))
                valueOfTruth = false;
        } else {
            throw new InvalidExpression("Expecting only #t or #f symbols for NOT operation.");
        }

        if (valueOfTruth)
            return new Symbol("#t");
        return new Symbol("#f");
    }
}
