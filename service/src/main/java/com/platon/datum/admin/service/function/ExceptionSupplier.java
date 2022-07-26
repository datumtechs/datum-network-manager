package com.platon.datum.admin.service.function;

@FunctionalInterface
public interface ExceptionSupplier<T>  {
    T get() throws Exception;
}
