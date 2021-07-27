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

    public <T extends SqlRepo, U> U transaction(TransactionBlock<T, U> fn) {
        try {
            return fn.begin((T) this);
        } catch (ClassCastException e) {
            //TODO: handle class cast exception
            throw new
        } catch (Throwable throwable) {
            return throwChecked(throwable);
        }
    }
}
