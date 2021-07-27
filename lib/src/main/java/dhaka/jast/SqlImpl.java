package dhaka.jast;

import com.google.common.collect.Lists;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

class SqlImpl implements Sql {
    private final String sql;
    private final DataSource dataSource;
    private final List<Consumer<PreparedStatement>> consumers = Lists.newArrayList();

    SqlImpl(String sql, DataSource dataSource) {
        this.sql = sql;
        this.dataSource = dataSource;
    }

    @Override
    public OptionalResult<Integer> executeUpdate() {
        if (ThreadTransactionMap.containsTransactionInProgress()) {
            //don't close connection
            return getSqlResult(ThreadTransactionMap.get());
        } else {
            //close connection
            try (var con = dataSource.getConnection()) {
                return getSqlResult(con);
            } catch (SQLException exception) {
                return OptionalResult.error(exception);
            }
        }
    }

    private OptionalResult<Integer> getSqlResult(Connection con) {
        try (var pst = con.prepareStatement(sql)) {
            for (var pstConsumer : consumers) {
                pstConsumer.accept(pst);
            }
            return OptionalResult.of(pst.executeUpdate());
        } catch (SQLException exception) {
            return OptionalResult.error(exception);
        }
    }

    @Override
    public Sql bind(int i, String value) {
        return bind(Unchecked.consume(pst -> pst.setString(i, value)));
    }

    @Override
    public Sql bind(int i, Long value) {
        return bind(Unchecked.consume(pst -> pst.setLong(i, value)));
    }

    @Override
    public Sql bind(int i, Integer value) {
        return bind(Unchecked.consume(pst -> pst.setInt(i, value)));
    }

    @Override
    public Sql bind(int i, Boolean value) {
        return bind(Unchecked.consume(pst -> pst.setBoolean(i, value)));
    }

    @Override
    public void startTransaction() {
        try {
            //this connection is not closed by intention
            //it is supposed to be shared by multiple method calls in same thread
            //before finally committing or closing
            var connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            ThreadTransactionMap.put(connection);
        } catch (SQLException throwable) {
            Unchecked.throwChecked(throwable);
        }
    }

    @Override
    public void commit() {
        try (var connection = ThreadTransactionMap.get()) {
            if (connection != null) {
                connection.commit();
            }
        } catch (SQLException throwable) {
            Unchecked.throwChecked(throwable);
        }
    }

    @Override
    public void rollback() {
        try (var connection = ThreadTransactionMap.get()) {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException throwable) {
            Unchecked.throwChecked(throwable);
        }
    }

    @Override
    public <T> MappedRow<T> withConverter(RowMapper<T> rowMapper) {
        return new MappedRowImpl<>(sql, rowMapper, dataSource, consumers);
    }

    private Sql bind(Consumer<PreparedStatement> consumer) {
        consumers.add(consumer);
        return this;
    }
}