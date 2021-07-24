package dhaka.jast;

import javax.sql.DataSource;

public class SqlRepo {
    private final DataSource dataSource;

    public SqlRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SQL sql(String sql) {
        return new SQLImpl(sql, dataSource);
    }

    public static SQL sql(DataSource dataSource, String sql) {
        return new SQLImpl(sql, dataSource);
    }
}
