package org.racketsimulator.callable.builtin.arithmetic.booleans.logical;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.expression.*;

import java.util.List;
import java.util.Objects;

public class And implements Callable {
    /**
     * @param args Arguments that its evaluation must return a Symbol of #t or #f.
     * @return Symbol of type #t or #f.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (args.isEmpty())
            throw new InvalidExpression("For AND operation is required one or more Symbols of type #t or #f");

        boolean valueOfTruth = true;
        for (Expression arg : args) {
            if (arg instanceof SExpression) {
                var pivot = arg.evaluate();
                if (pivot instanceof Symbol) {
                    if (!Objects.equals(pivot.content(), "#f") && !Objects.equals(pivot.content(), "#t"))
                        throw new InvalidExpression("Expecting only #t or #f symbols for AND operation.");
                    if (Objects.equals(pivot.content(), "#f"))
                        valueOfTruth = false;
                } else {
                    throw new InvalidExpression("Expecting only #t or #f symbols for AND operation.");
                }
            } else if (arg instanceof Symbol) {
                if (!Objects.equals(arg.content(), "#f") && !Objects.equals(arg.content(), "#t"))
                    throw new InvalidExpression("Expecting only #t or #f symbols for AND operation.");
                if (Objects.equals(arg.content(), "#f"))
                    valueOfTruth = false;
            } else {
                throw new InvalidExpression("Expecting only #t or #f symbols for AND operation.");
            }
        }

        if (valueOfTruth)
            return new Symbol("#t");
        return new Symbol("#f");
    }
}
