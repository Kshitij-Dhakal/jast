package dhaka.jast;

import java.util.function.Function;

interface Result<T, U> {
    /**
     * get value
     * used for ignoring exceptions
     *
     * @return value
     */
    T get();

    /**
     * get value only if exception doesn't occur
     * if exception occurs exception will be thrown
     *
     * @return value
     */
    U rethrowError();

    /**
     * get value even if exception occurred
     * if exception occurs passed mapping function is used to get fallback value
     *
     * @param fn mapping function
     * @return value
     */
    U exceptionally(Function<Throwable, T> fn);


    /**
     * get value only if exception doesn't occur
     * throws mapped exception instead
     *
     * @param fn mapping function
     * @param <E> exception type
     * @return value
     * @throws E mapped exception
     */
    <E extends Throwable> U catchAndRethrow(Function<Throwable, E> fn) throws E;
}
