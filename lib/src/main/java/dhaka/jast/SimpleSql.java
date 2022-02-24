package dhaka.jast;

public interface SimpleSql extends Sql {
    @Override
    SimpleSql bind(int i, String value);

    @Override
    SimpleSql bind(int i, long value);

    @Override
    SimpleSql bind(int i, int value);

    @Override
    SimpleSql bind(int i, boolean value);

    @Override
    SimpleSql bind(int i, byte value);

    @Override
    SimpleSql bind(int i, byte[] value);

    UpdateResult<Integer> executeUpdate();

    <T> MappedRow<T> withConverter(RowMapper<T> rowMapper);
}
