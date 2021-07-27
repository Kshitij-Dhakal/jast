package dhaka.jast;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static dhaka.jast.Unchecked.throwChecked;

class OptionalResultImpl<T> implements OptionalResult<T> {
    private final T value;
    private final Throwable throwable;

    OptionalResultImpl(T value, Throwable throwable) {
        this.value = value;
        this.throwable = throwable;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public Optional<T> optionally() {
        return Optional.of(value);
    }

    @Override
    public <E extends Throwable> Optional<T> catchError(Function<Throwable, E> fn) throws E {
        Objects.requireNonNull(fn);
        if (this.throwable != null) {
            throw fn.apply(this.throwable);
        }
        if (value == null) {
            throw fn.apply(new NoSuchElementException());
        }
        return optionally();
    }

    @Override
    public Optional<T> exceptionally(Function<Throwable, T> fn) {
        if (throwable != null) {
            return Optional.ofNullable(fn.apply(throwable));
        } else {
            return Optional.ofNullable(value);
        }
    }

    @Override
    public Optional<T> rethrowError() {
        if (throwable != null) {
            throwChecked(throwable);
        }
        return Optional.ofNullable(value);
    }
}
