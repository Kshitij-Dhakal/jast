package dhaka.jast;

import org.jooq.lambda.Unchecked;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowImpl implements Row {
    private final ResultSet resultSet;

    public RowImpl(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public String getString(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getString(columnLabel));
    }

    @Override
    public Long getLong(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getLong(columnLabel));
    }

    @Override
    public Integer getInt(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getInt(columnLabel));
    }

    @Override
    public Boolean getBoolean(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getBoolean(columnLabel));
    }

    private interface InternalRowMapper<T> {
        T unchecked(ResultSet rs) throws SQLException;
    }

    private <T> T unchecked(ResultSet rs, InternalRowMapper<T> internalRowMapper) {
        try {
            return internalRowMapper.unchecked(rs);
        } catch (SQLException e) {
            Unchecked.throwChecked(e);

            //method will not reach below
            return null;
        }
    }
}
