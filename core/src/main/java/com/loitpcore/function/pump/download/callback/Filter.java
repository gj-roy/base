package com.loitpcore.function.pump.download.callback;

public interface Filter<T> {
    boolean filter(T t);
}