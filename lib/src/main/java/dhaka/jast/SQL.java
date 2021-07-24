package dhaka.jast;

public interface SQL {
    int executeUpdate();

    SQL bind(int i, String value);

    SQL bind(int i, Long value);

    SQL bind(int i, Integer value);

    SQL bind(int i, Boolean value);

    <T> MappedRow<T> withConverter(RowMapper<T> rowMapper);
}
