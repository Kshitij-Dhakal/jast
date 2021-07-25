package dhaka.jast;

import java.util.function.Function;

//Result will only be used for holding value or throwable
//Result holds either Optional or List, so it is decided to not add map, flatMap, or, orElse and any other methods
// that are present in Optional and List.
//Result more closely resembles CompletableFuture then Optional
public interface Result<T> {
    static <T> ResultImpl<T> of(T value) {
        return new ResultImpl<>(value, null);
    }

    static <T> ResultImpl<T> error(Throwable throwable) {
        return new ResultImpl<>(null, throwable);
    }

    //get value
    //used for ignoring exceptions
    T get();

    T exceptionally(Function<Throwable, T> fn);

    //throw exception
    T orElseThrow();

    <E extends Exception> T orElseThrow(Function<Throwable, E> fn) throws E;
}
