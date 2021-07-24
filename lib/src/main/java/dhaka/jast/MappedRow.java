package dhaka.jast;

import java.util.List;
import java.util.Optional;

public interface MappedRow<T> {
    List<T> findAll();

    Optional<T> findFirst();
}
