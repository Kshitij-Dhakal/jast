package dhaka.jast;

import com.google.common.collect.Lists;
import org.jooq.lambda.Unchecked;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This class is where all the jdbc code will be implemented
 *
 * @param <T>
 */
public class MappedRowImpl<T> implements MappedRow<T> {
    private final String sql;
    private final RowMapper<T> rowMapper;
    private final DataSource dataSource;
    private final List<Consumer<PreparedStatement>> consumers;

    public MappedRowImpl(String sql, RowMapper<T> rowMapper, DataSource dataSource,
                         @Nonnull List<Consumer<PreparedStatement>> consumers) {
        this.sql = sql;
        this.rowMapper = rowMapper;
        this.dataSource = dataSource;
        this.consumers = consumers;
    }


    @Override
    public List<T> findAll() {
        return getU(resultSet -> {
            List<T> mappedRows = Lists.newArrayList();
            while (next(resultSet)) {
                var row = new RowImpl(resultSet);
                var map = rowMapper.map(row);
                mappedRows.add(map);
            }
            return mappedRows;
        });
    }

    private ResultSet getResultSet(PreparedStatement pst) throws SQLException {
        for (var pstConsumer : consumers) {
            pstConsumer.accept(pst);
        }
        return pst.executeQuery();
    }

    @Override
    public Optional<T> findFirst() {
        return getU(rs -> {
            T map = null;
            if (next(rs)) {
                map = rowMapper.map(new RowImpl(rs));
            }
            return Optional.ofNullable(map);
        });
    }

    private boolean next(ResultSet rs) {
        return Unchecked.supplier(rs::next).get().equals(Boolean.TRUE);
    }

    private <U> U getU(Function<ResultSet, U> function) {
        try (var con = dataSource.getConnection();
             var pst = con.prepareStatement(sql);
             var rs = getResultSet(pst)) {
            return function.apply(rs);
        } catch (SQLException exception) {
            Unchecked.throwChecked(exception);

            //method will not reach following statement
            return null;
        }
    }
}
