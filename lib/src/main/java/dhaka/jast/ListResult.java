package dhaka.jast;

import java.util.List;

public interface ListResult<T> extends Result<List<T>, List<T>> {

    static <T> ListResult<T> of(List<T> value) {
        return new ListResultImpl<>(value, null);
    }

    static <T> ListResult<T> error(Throwable throwable) {
        return new ListResultImpl<>(null, throwable);
    }
}
