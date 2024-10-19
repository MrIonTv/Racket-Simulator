package org.racketsimulator.callable;

import org.racketsimulator.expression.*;

import java.util.List;

public class DefinedCallable implements Callable{
    private final String body;

    public DefinedCallable(String body) {
        this.body = body;
    }

    /**
     * @param args Not used.
     * @return The body of the callable.
     */
    @Override
    public Expression execute(List<Expression> args) {
        if (!args.isEmpty())
            throw new InvalidCallableArgs("DefinedCallable requires exactly 0 args");

        /*
        if (this.body.isEmpty())
            return new Empty();
        if (this.body.matches("-?\\d+"))
            return new Numeric(Integer.parseInt(this.body));
        */
        return new Symbol(this.body);
    }
}
