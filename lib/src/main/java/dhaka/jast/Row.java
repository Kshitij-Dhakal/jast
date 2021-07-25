package dhaka.jast;

public interface Row {
    String getString(String columnLabel);

    String getString(int columnIndex);

    Long getLong(String columnLabel);

    Long getLong(int columnIndex);

    Integer getInt(String columnLabel);

    Integer getInt(int columnIndex);

    Boolean getBoolean(String columnLabel);

    Boolean getBoolean(int columnIndex);
}
