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
