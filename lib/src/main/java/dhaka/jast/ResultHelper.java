package dhaka.jast;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static dhaka.jast.Unchecked.throwChecked;

final class ResultHelper {
    private ResultHelper() {
        //no instance
    }

    static <U> U exceptionally(Function<Throwable, U> fn, Throwable throwable, Supplier<U> supplier) {
        Objects.requireNonNull(fn);
        if (throwable != null) {
            return fn.apply(throwable);
        } else {
            return supplier.get();
        }
    }

    static <U> U rethrowError(Throwable throwable, Supplier<U> supplier) {
        if (throwable != null) {
            //doesn't returns anything
            //throws checked exception
            return throwChecked(throwable);
        }
        return supplier.get();
    }

    static <U, E extends Throwable> U catchAndRethrow(Function<Throwable, E> fn, Throwable throwable,
                                                      Supplier<U> supplier) throws E {
        Objects.requireNonNull(fn);
        if (throwable != null) {
            throw fn.apply(throwable);
        }
        return supplier.get();
    }
}
