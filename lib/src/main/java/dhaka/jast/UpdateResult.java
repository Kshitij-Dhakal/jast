package dhaka.jast;

public interface UpdateResult<T> extends Result<T, T> {
    static <T> UpdateResult<T> of(T value) {
        return new UpdateResultImpl<>(value, null);
    }

    static <T> UpdateResult<T> error(Throwable throwable) {
        return new UpdateResultImpl<>(null, throwable);
    }
}
