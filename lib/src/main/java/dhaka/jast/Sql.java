package dhaka.jast;

public interface Sql {
    UpdateResult<Integer> executeUpdate();

    Sql bind(int i, String value);

    Sql bind(int i, Long value);

    Sql bind(int i, Integer value);

    Sql bind(int i, Boolean value);

    void startTransaction();

    void commit();

    void rollback();

    <T> MappedRow<T> withConverter(RowMapper<T> rowMapper);
}
