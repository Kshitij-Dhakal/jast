package dhaka.jast;

import java.util.List;
import java.util.Optional;

public interface MappedRow<T> {
    Result<List<T>> findAll();

    Result<Optional<T>> findFirst();
}
