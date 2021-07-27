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
        if (throwable != null) {
            return fn.apply(throwable);
        }
        return value;
    }

    @Override
    public T rethrowError() {
        if (throwable != null) {
            Unchecked.throwChecked(throwable);
        }
        return value;
    }

    @Override
    public <E extends Throwable> T catchError(Function<Throwable, E> fn) throws E {
        if (throwable != null) {
            throw fn.apply(throwable);
        }
        return get();
    }
}
