package org.racketsimulator.expressionbuilder;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Empty;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Numeric;
import org.racketsimulator.expression.Symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneralParser implements ExpressionBuilder{
    protected static final String OPEN = "(";
    protected static final String CLOSE = ")";
    protected static final String SPACE = " ";
    protected final Environment runtime;

    public GeneralParser(Environment runtime) {
        this.runtime = runtime;
    }

    @Override
    public Expression build(String sentence) {
        return null;
    }


    protected List<String> tokenise(String sentence) {
        return Arrays.stream(
                        sentence.replace(OPEN, SPACE + OPEN + SPACE)
                                .replace(CLOSE, SPACE + CLOSE + SPACE)
                                .split("\\s+"))
                                .filter(s -> !s.isBlank())
                                .collect(Collectors.toList());
    }

    protected Expression evalToken(String token) {
        if (token.isEmpty())
            return new Empty();
        if (checkNumber(token))
            return new Numeric(Integer.parseInt(token));
        return evalSymbol(new Symbol(token));
    }

    protected Expression evalSymbol(Symbol token) {
        Optional<Callable> value = runtime.search(token);
        if (value.isEmpty())
            return token;
        return new Symbol(value.get().execute(new ArrayList<>()).content());

    }

    protected boolean checkNumber(String value) {
        return value.matches("-?\\d+");
    }
}
