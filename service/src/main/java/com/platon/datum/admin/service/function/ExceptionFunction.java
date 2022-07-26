package com.platon.datum.admin.service.function;

@FunctionalInterface
public interface ExceptionFunction<T, R>  {

    R apply(T t) throws Exception;
}
