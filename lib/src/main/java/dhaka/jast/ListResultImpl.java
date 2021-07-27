package dhaka.jast;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;

import static dhaka.jast.Unchecked.throwChecked;

public class ListResultImpl<T> implements ListResult<T> {
    private final List<T> list;
    private final Throwable throwable;

    public ListResultImpl(List<T> list, Throwable throwable) {
        this.list = list;
        this.throwable = throwable;
    }

    @Override
    public List<T> get() {
        return list == null ? Lists.newArrayList() : list;
    }

    @Override
    public List<T> exceptionally(Function<Throwable, List<T>> fn) {
        Objects.requireNonNull(fn);
        if (throwable != null) {
            return fn.apply(throwable);
        } else {
            return get();
        }
    }

    @Override
    public List<T> rethrowError() {
        if (throwable != null) {
            throwChecked(throwable);
        }
        return list;
    }

    @Override
    public <E extends Throwable> List<T> catchError(Function<Throwable, E> fn) throws E {
        Objects.requireNonNull(fn);
        if (this.throwable != null) {
            throw fn.apply(this.throwable);
        }
        if (list == null) {
            throw fn.apply(new NoSuchElementException());
        }
        return get();
    }
}
