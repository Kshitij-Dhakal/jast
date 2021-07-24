package dhaka.jast;

public interface Row {
    String getString(String columnLabel);

    Long getLong(String columnLabel);

    Integer getInt(String columnLabel);

    Boolean getBoolean(String columnLabel);
}
