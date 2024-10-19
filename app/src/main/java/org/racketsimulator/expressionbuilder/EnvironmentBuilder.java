package org.racketsimulator.expressionbuilder;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnvironmentBuilder extends GeneralParser {
    private final List<Expression> args;

    public EnvironmentBuilder(List<Expression> args, Environment runtime) {
        super(runtime);
        this.args = args;
    }

    /**
     * @param sentence The Symbol value returned by the DefinedCallable.
     * @return a repaired SExpression.
     */
    @Override
    public Expression build(String sentence) {
        if (checkNumber(sentence))
            return new Numeric(Integer.parseInt(sentence));

        List<String> tokens = tokenise(sentence);
        int minim = args.size();
        int tokenSize = tokens.size();
        if (minim > tokenSize)
            throw new InvalidCallableArgs("DefinedCallable requires the same or less number" +
                    " of args to their body args.");

        List<Expression> expressions = new ArrayList<>();
        expressions.add(new Symbol(tokens.getFirst()));
        for (int i = 1; i < tokenSize; i++) {
            if (i < minim) {
                expressions.add(args.get(i));
            } else {
                expressions.add(evalToken(tokens.get(i)));
            }
        }

        return new SExpression(expressions, runtime).evaluate();
    }

}

