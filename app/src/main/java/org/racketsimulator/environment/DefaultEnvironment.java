package org.racketsimulator.environment;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.expression.Symbol;

import java.util.HashMap;
import java.util.Optional;

public class DefaultEnvironment implements Environment{
    private final HashMap<Symbol, Callable> callables;
    private final Environment systemEnvironment;

    public DefaultEnvironment(HashMap<Symbol, Callable> callables, Environment systemEnvironment) {
        this.callables = callables;
        this.systemEnvironment = systemEnvironment;
    }

    public DefaultEnvironment() {
        this.callables = new HashMap<Symbol, Callable>();
        this.systemEnvironment = null;
    }

    /**
     * @param symbol The symbol that the callable will be related.
     * @param callable The callable related to a symbol.
     */
    @Override
    public void defineSymbol(Symbol symbol, Callable callable) {
        Optional<Callable> existent = systemEnvironment.search(symbol);
        if (existent.isPresent()){
            callables.put(symbol, callable);
            throw new OverwriteSymbol("The Symbol: " + symbol.content() + ", has overwritten a builtin symbol.");
        }
        existent = search(symbol);
        if (existent.isPresent())
            throw new UsedSymbol("The Symbol: " + symbol.content() + ", has already been defined.");

        callables.put(symbol, callable);
    }

    /**
     * @param symbol the key to access to a callable.
     * @return a callable.
     */
    @Override
    public Optional<Callable> search(Symbol symbol) {
        Callable result = callables.getOrDefault(symbol, null);
        if (result != null)
            return Optional.of(result);
        return Optional.empty();
    }
}
