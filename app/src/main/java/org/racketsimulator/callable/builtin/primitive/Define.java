package org.racketsimulator.callable.builtin.primitive;

import org.racketsimulator.callable.InvalidCallableArgs;
import org.racketsimulator.callable.builtin.BuiltinCallable;
import org.racketsimulator.callable.builtin.DefinedCallable;
import org.racketsimulator.environment.Environment;
import org.racketsimulator.expression.Expression;
import org.racketsimulator.expression.Symbol;

import java.util.List;

public class Define extends BuiltinCallable {
    public Define(Environment runtime) {
        super(runtime);
    }

    @Override
    public Expression execute(List<Expression> args) {
        if (args.size() != 2)
            throw new InvalidCallableArgs("Operation define can needs exactly 2 arguments.");
        if (!(args.getFirst() instanceof Symbol))
            throw new InvalidCallableArgs("First argument for define, must be a Symbol.");
        super.runtime.defineSymbol((Symbol) args.getFirst(), new DefinedCallable(super.runtime, args.getLast().content()));
        return new Symbol("<Procedure>: " + args.getFirst().stringContent() + ", defined as: "
        + args.getLast().stringContent());
    }
}
