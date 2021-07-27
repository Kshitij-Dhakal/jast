package dhaka.jast;

import java.util.Optional;

//Result will only be used for holding value or throwable
//Result holds either Optional or List, so it is decided to not add map, flatMap, or, orElse and any other methods
// that are present in Optional and List.
//Result more closely resembles CompletableFuture then Optional
public interface OptionalResult<T> extends Result<T, Optional<T>> {
    static <T> OptionalResultImpl<T> of(T value) {
        return new OptionalResultImpl<>(value, null);
    }

    static <T> OptionalResultImpl<T> error(Throwable throwable) {
        return new OptionalResultImpl<>(null, throwable);
    }

    //get optional value
    Optional<T> optionally();
}
