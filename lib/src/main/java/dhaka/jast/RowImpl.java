package dhaka.jast;

import java.sql.ResultSet;
import java.sql.SQLException;

import static dhaka.jast.Unchecked.throwChecked;

class RowImpl implements Row {
    private final ResultSet resultSet;

    public RowImpl(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public String getString(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getString(columnLabel));
    }

    @Override
    public String getString(int columnLabel) {
        return unchecked(resultSet, rs -> rs.getString(1));
    }

    @Override
    public Long getLong(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getLong(columnLabel));
    }

    @Override
    public Long getLong(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getLong(columnIndex));
    }

    @Override
    public Integer getInt(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getInt(columnLabel));
    }

    @Override
    public Integer getInt(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getInt(columnIndex));
    }

    @Override
    public Boolean getBoolean(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getBoolean(columnLabel));
    }

    @Override
    public Boolean getBoolean(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getBoolean(columnIndex));
    }

    private interface InternalRowMapper<T> {
        T unchecked(ResultSet rs) throws SQLException;
    }

    private <T> T unchecked(ResultSet rs, InternalRowMapper<T> internalRowMapper) {
        try {
            return internalRowMapper.unchecked(rs);
        } catch (SQLException e) {
            throwChecked(e);

            //method will not reach below
            return null;
        }
    }
}
