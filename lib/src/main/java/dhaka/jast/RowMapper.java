package dhaka.jast;

public interface RowMapper<T> {
    T map(Row row);
}
