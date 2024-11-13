package org.racketsimulator.expressionbuilder;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.DefinedCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.*;

import java.util.*;

public class EnvironmentParser extends DefaultParser {
    private final List<Expression> args;

    public EnvironmentParser(List<Expression> args, Environment runtime) {
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
        Map<String, Expression> seenExpressions = new HashMap<>();
        expressions.add(new Symbol(tokens.getFirst()));
        for (int i = 1; i < tokenSize; i++) {
            Expression token = evalToken(tokens.get(i));
            Expression seen = token;
            if (seenExpressions.containsKey(seen.stringContent())) {
                token = seenExpressions.get(seen.stringContent());
            } else if (token instanceof Symbol) {
                Optional<Callable> environmentSymbol = runtime.search((Symbol) token);
                if (environmentSymbol.isPresent() && environmentSymbol.get() instanceof DefinedCallable) {
                    token = environmentSymbol.get().execute(List.of());
                } else {
                    try {
                        token = args.removeFirst();
                        if (token instanceof Symbol) {
                            environmentSymbol = runtime.search((Symbol) token);
                            if (environmentSymbol.isPresent())
                                token = environmentSymbol.get().execute(List.of());
                        }
                    } catch (Exception e) {
                        throw new InvalidCallableArgs("Insufficient args passed for Procedure");
                    }
                }
            }
            seenExpressions.put(seen.stringContent(), token);
            expressions.add(token);
        }

        return new SExpression(expressions, runtime).evaluate();
    }
}

//(define fun
//        (cond
//    ((= x 1) 1)
//        (else (fun (- x 1)))))