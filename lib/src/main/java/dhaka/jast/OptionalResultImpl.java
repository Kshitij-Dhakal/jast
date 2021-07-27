package dhaka.jast;

import java.util.Optional;
import java.util.function.Function;

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
    public <E extends Throwable> Optional<T> catchAndRethrow(Function<Throwable, E> fn) throws E {
        return ResultHelper.catchAndRethrow(fn, throwable, this::optionally);
    }

    @Override
    public Optional<T> exceptionally(Function<Throwable, T> fn) {
        return ResultHelper.exceptionally(e -> Optional.ofNullable(fn.apply(e)), throwable, this::optionally);
    }

    @Override
    public Optional<T> rethrowError() {
        return ResultHelper.rethrowError(throwable, this::optionally);
    }
}
