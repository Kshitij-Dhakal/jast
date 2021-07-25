package dhaka.jast;

import com.google.common.collect.Lists;
import org.jooq.lambda.Unchecked;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

class SQLImpl implements SQL {
    private final String sql;
    private final DataSource dataSource;
    private final List<Consumer<PreparedStatement>> consumers = Lists.newArrayList();

    SQLImpl(String sql, DataSource dataSource) {
        this.sql = sql;
        this.dataSource = dataSource;
    }

    @Override
    public Result<Integer> executeUpdate() {
        try (var con = dataSource.getConnection();
             var pst = con.prepareStatement(sql)) {
            for (var pstConsumer : consumers) {
                pstConsumer.accept(pst);
            }
            return Result.of(pst.executeUpdate());
        } catch (SQLException exception) {
            return Result.error(exception);
        }
    }

    @Override
    public SQL bind(int i, String value) {
        return bind(Unchecked.consumer(pst -> pst.setString(i, value)));
    }

    @Override
    public SQL bind(int i, Long value) {
        return bind(Unchecked.consumer(pst -> pst.setLong(i, value)));
    }

    @Override
    public SQL bind(int i, Integer value) {
        return bind(Unchecked.consumer(pst -> pst.setInt(i, value)));
    }

    @Override
    public SQL bind(int i, Boolean value) {
        return bind(Unchecked.consumer(pst -> pst.setBoolean(i, value)));
    }

    @Override
    public <T> MappedRow<T> withConverter(RowMapper<T> rowMapper) {
        return new MappedRowImpl<>(sql, rowMapper, dataSource, consumers);
    }

    private SQL bind(Consumer<PreparedStatement> consumer) {
        consumers.add(consumer);
        return this;
    }
}
