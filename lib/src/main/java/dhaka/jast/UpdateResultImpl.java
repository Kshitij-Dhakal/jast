package dhaka.jast;

import java.util.function.Function;

class UpdateResultImpl<T> implements UpdateResult<T> {
    private final T value; //either int or int[]
    private final Throwable throwable;

    UpdateResultImpl(T value, Throwable throwable) {
        this.value = value;
        this.throwable = throwable;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T exceptionally(Function<Throwable, T> fn) {
        return ResultHelper.exceptionally(fn, throwable, this::get);
    }

    @Override
    public T rethrowError() {
        return ResultHelper.rethrowError(throwable, this::get);
    }

    @Override
    public <E extends Throwable> T catchAndRethrow(Function<Throwable, E> fn) throws E {
        return ResultHelper.catchAndRethrow(fn, throwable, this::get);
    }
}
