package io.github.betterclient.groupadle.util;

import java.util.function.Supplier;

public class OutputReset<T> {
    private T currentInstance;
    private final Supplier<T> supplier;

    public OutputReset(Supplier<T> instance) {
        this.supplier = instance;

        this.currentInstance = instance.get();
    }

    public T getInstance() {
        return currentInstance;
    }

    public void reset() {
        this.currentInstance = this.supplier.get();
    }
}
