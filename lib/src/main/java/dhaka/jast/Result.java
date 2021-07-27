package dhaka.jast;

import java.util.function.Function;

interface Result<T, U> {
    //get value
    //used for ignoring exceptions
    T get();

    //get value
    //if exception occurs passed mapping function is used to get fallback value
    U exceptionally(Function<Throwable, T> fn);

    U rethrowError();

    //throw exception
    <E extends Throwable> U catchError(Function<Throwable, E> fn) throws E;
}
