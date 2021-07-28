package dhaka.jast;

public interface Sql {
    UpdateResult<Integer> executeUpdate();

    Sql bind(int i, String value);

    Sql bind(int i, long value);

    Sql bind(int i, int value);

    Sql bind(int i, boolean value);

    Sql bind(int i, byte value);

    Sql bind(int i, byte[] value);

    <T> MappedRow<T> withConverter(RowMapper<T> rowMapper);
}
