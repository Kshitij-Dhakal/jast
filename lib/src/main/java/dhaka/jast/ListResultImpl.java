package dhaka.jast;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;

class ListResultImpl<T> implements ListResult<T> {
    private final List<T> list;
    private final Throwable throwable;

    ListResultImpl(List<T> list, Throwable throwable) {
        this.list = list;
        this.throwable = throwable;
    }

    @Override
    public List<T> get() {
        return list == null ? Lists.newArrayList() : list;
    }

    @Override
    public List<T> exceptionally(Function<Throwable, List<T>> fn) {
        return ResultHelper.exceptionally(fn, throwable, this::get);
    }

    @Override
    public List<T> rethrowError() {
        return ResultHelper.rethrowError(throwable, this::get);
    }

    @Override
    public <E extends Throwable> List<T> catchAndRethrow(Function<Throwable, E> fn) throws E {
        return ResultHelper.catchAndRethrow(fn, throwable, this::get);
    }
}
