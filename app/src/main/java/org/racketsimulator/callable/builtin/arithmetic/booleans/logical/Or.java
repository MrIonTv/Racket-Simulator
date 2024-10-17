package org.racketsimulator.callable.builtin.arithmetic.booleans.logical;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.InvalidExpression;
import org.racketsimulator.expression.SExpression;
import org.racketsimulator.expression.Symbol;

import java.util.List;
import java.util.Objects;

public class Or implements Callable {

    /**
     * @param args
     * @return
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (args.isEmpty())
            throw new InvalidExpression("For OR operation is required one or more boolean arguments (#t/#f)");

        boolean valueOfTruth = false;
        for (Expression arg : args) {
            if (arg instanceof SExpression) {
                var pivot = arg.evaluate();
                if (pivot instanceof Symbol) {
                    if (!Objects.equals(pivot.content(), "#f") && !Objects.equals(pivot.content(), "#t"))
                        throw new InvalidExpression("Expecting only booleans symbols for AND operation.");
                    if (Objects.equals(pivot.content(), "#t"))
                        valueOfTruth = true;
                } else {
                    throw new InvalidExpression("Expecting only booleans symbols for AND operation.");
                }
            } else if (arg instanceof Symbol) {
                if (!Objects.equals(arg.content(), "#f") && !Objects.equals(arg.content(), "#t"))
                    throw new InvalidExpression("Expecting only booleans symbols for AND operation.");
                if (Objects.equals(arg.content(), "#t"))
                    valueOfTruth = true;
            } else {
                throw new InvalidExpression("Expecting only booleans symbols for AND operation.");
            }
        }

        if (valueOfTruth)
            return new Symbol("#t");
        return new Symbol("#f");
    }
}
