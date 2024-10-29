package org.racketsimulator.environment;

import org.racketsimulator.callable.Callable;
import org.racketsimulator.expression.Symbol;

import java.util.HashMap;
import java.util.Optional;

public class DefaultEnvironment implements Environment{
    private final HashMap<String, Callable> callables;

    public DefaultEnvironment() {
        this.callables = new HashMap<>();
    }

    /**
     * @param symbol The symbol that the callable will be related.
     * @param callable The callable related to a symbol.
     */
    @Override
    public void defineSymbol(Symbol symbol, Callable callable) {
        Optional<Callable> existent;

        existent = search(symbol);
        if (existent.isPresent())
            throw new UsedSymbol("The Symbol: " + symbol.content() + ", has already been defined.");

        callables.put(symbol.stringContent(), callable);
    }

    /**
     * @param symbol the key to access to a callable.
     * @return a callable.
     */
    @Override
    public Optional<Callable> search(Symbol symbol) {
        String key = symbol.stringContent();
        Callable result = callables.getOrDefault(key, null);
        if (result != null)
            return Optional.of(result);
        return Optional.empty();
    }
}
