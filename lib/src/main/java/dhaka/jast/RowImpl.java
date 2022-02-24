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
    public long getLong(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getLong(columnLabel));
    }

    @Override
    public long getLong(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getLong(columnIndex));
    }

    @Override
    public int getInt(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getInt(columnLabel));
    }

    @Override
    public int getInt(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getInt(columnIndex));
    }

    @Override
    public boolean getBoolean(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getBoolean(columnLabel));
    }

    @Override
    public boolean getBoolean(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getBoolean(columnIndex));
    }

    @Override
    public byte getByte(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getByte(columnLabel));
    }

    @Override
    public byte getByte(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getByte(columnIndex));
    }

    @Override
    public byte[] getBytes(String columnLabel) {
        return unchecked(resultSet, rs -> rs.getBytes(columnLabel));
    }

    @Override
    public byte[] getBytes(int columnIndex) {
        return unchecked(resultSet, rs -> rs.getBytes(columnIndex));
    }

    private interface InternalRowMapper<T> {
        T unchecked(ResultSet rs) throws SQLException;
    }

    private <T> T unchecked(ResultSet rs, InternalRowMapper<T> internalRowMapper) {
        try {
            return internalRowMapper.unchecked(rs);
        } catch (SQLException e) {
            return throwChecked(e);
        }
    }
}
