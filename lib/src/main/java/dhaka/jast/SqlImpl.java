package dhaka.jast;

import com.google.common.collect.Lists;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

import static dhaka.jast.Unchecked.consume;

class SqlImpl implements SimpleSql {
    private final String sql;
    private final DataSource dataSource;
    private final List<Consumer<PreparedStatement>> consumers = Lists.newArrayList();

    SqlImpl(String sql, DataSource dataSource) {
        this.sql = sql;
        this.dataSource = dataSource;
    }

    @Override
    public UpdateResult<Integer> executeUpdate() {
        try (var con = dataSource.getConnection()) {
            return getSqlResult(con);
        } catch (SQLException exception) {
            return UpdateResult.error(exception);
        }
    }

    private UpdateResult<Integer> getSqlResult(Connection con) {
        try (var pst = con.prepareStatement(sql)) {
            for (var pstConsumer : consumers) {
                pstConsumer.accept(pst);
            }
            return UpdateResult.of(pst.executeUpdate());
        } catch (SQLException exception) {
            return UpdateResult.error(exception);
        }
    }

    @Override
    public SimpleSql bind(int i, String value) {
        return bind(consume(pst -> pst.setString(i, value)));
    }

    @Override
    public SimpleSql bind(int i, long value) {
        return bind(consume(pst -> pst.setLong(i, value)));
    }

    @Override
    public SimpleSql bind(int i, int value) {
        return bind(consume(pst -> pst.setInt(i, value)));
    }

    @Override
    public SimpleSql bind(int i, boolean value) {
        return bind(consume(pst -> pst.setBoolean(i, value)));
    }

    @Override
    public SimpleSql bind(int i, byte value) {
        return bind(consume(pst -> pst.setByte(i, value)));
    }

    @Override
    public SimpleSql bind(int i, byte[] value) {
        return bind(consume(pst -> pst.setBytes(i, value)));
    }

    @Override
    public <T> MappedRow<T> withConverter(RowMapper<T> rowMapper) {
        return new MappedRowImpl<>(sql, rowMapper, dataSource, consumers);
    }

    private SimpleSql bind(Consumer<PreparedStatement> consumer) {
        consumers.add(consumer);
        return this;
    }
}
