package org.racketsimulator.callable.builtin.arithmetic.booleans.logical;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
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
            throw new InvalidCallableArgs("For AND operation is required one or args");

        boolean valueOfTruth = true;
        for (Expression arg : args) {
            if (arg instanceof SExpression) {
                var pivot = arg.evaluate();
                if (pivot instanceof Symbol) {
                    if (!Objects.equals(pivot.stringContent(), "#f") && !Objects.equals(pivot.stringContent(), "#t"))
                        throw new InvalidCallableArgs("Expecting only #t or #f symbols for AND operation.");
                    if (Objects.equals(pivot.stringContent(), "#f"))
                        valueOfTruth = false;
                } else {
                    throw new InvalidCallableArgs("Expecting only #t or #f symbols for AND operation.");
                }
            } else if (arg instanceof Symbol) {
                if (!Objects.equals(arg.stringContent(), "#f") && !Objects.equals(arg.stringContent(), "#t"))
                    throw new InvalidCallableArgs("Expecting only #t or #f symbols for AND operation.");
                if (Objects.equals(arg.stringContent(), "#f"))
                    valueOfTruth = false;
            } else {
                throw new InvalidCallableArgs("Expecting only #t or #f symbols for AND operation.");
            }
        }

        if (valueOfTruth)
            return new Symbol("#t");
        return new Symbol("#f");
    }
}
