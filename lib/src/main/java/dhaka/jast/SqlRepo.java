package dhaka.jast;

import javax.sql.DataSource;
import java.util.Objects;

public class SqlRepo {
    private final DataSource dataSource;

    public SqlRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected SimpleSql sql(String sql) {
        Objects.requireNonNull(sql);
        return new SqlImpl(sql, dataSource);
    }

    protected Transaction begin() {
        return new TransactionImpl(dataSource);
    }
}
