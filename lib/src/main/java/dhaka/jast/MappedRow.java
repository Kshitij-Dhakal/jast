package dhaka.jast;

public interface MappedRow<T> {
    ListResult<T> findAll();

    OptionalResult<T> findFirst();
}
