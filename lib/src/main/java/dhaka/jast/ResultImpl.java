package dhaka.jast;

import org.jooq.lambda.Unchecked;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

class ResultImpl<T> implements Result<T> {
    private final T value;
    private final Throwable throwable;

    ResultImpl(T value, Throwable throwable) {
        this.value = value;
        this.throwable = throwable;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElseThrow() {
        if (throwable != null) {
            Unchecked.throwChecked(throwable);
        }
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Function<Throwable, E> fn) throws E {
        Objects.requireNonNull(fn);
        if (this.throwable != null) {
            throw fn.apply(this.throwable);
        }
        if (value == null) {
            throw fn.apply(new NoSuchElementException());
        }
        return get();
    }

    @Override
    public T exceptionally(Function<Throwable, T> fn) {
        if (throwable != null) {
            return fn.apply(throwable);
        } else {
            return value;
        }
    }
}
