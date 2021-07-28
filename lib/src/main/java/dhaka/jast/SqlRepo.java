package dhaka.jast;

import javax.sql.DataSource;
import java.util.Objects;

import static dhaka.jast.JastCommons.throwChecked;

public class SqlRepo {
    private final DataSource dataSource;

    public SqlRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Sql sql(String sql) {
        Objects.requireNonNull(sql);
        return new SqlImpl(sql, dataSource);
    }

    public <U, E extends Throwable> U transactional(TransactionBlock<U, E> fn) {
        try (var conn = dataSource.getConnection()) {
            return fn.begin(new TransactionImpl(conn));
        } catch (Throwable throwable) {
            return throwChecked(throwable);
        }
    }
}
