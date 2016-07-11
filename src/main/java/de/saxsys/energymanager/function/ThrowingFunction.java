/*
 * This document contains trade secret data which is the property of
 * IAV GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from IAV GmbH.
 *
 * Copyright (C) IAV GmbH / Gifhorn / Germany
 */
package de.saxsys.energymanager.function;

/**
 * A {@link java.util.function.Function} that throws an exception of type E.
 *
 * @param <T>
 * @param <R>
 * @param <E>
 */
public interface ThrowingFunction<T, R, E extends Throwable> {
  R apply(T t) throws E;
}
