package dhaka.jast;

public interface Row {
    String getString(String columnLabel);

    String getString(int columnIndex);

    long getLong(String columnLabel);

    long getLong(int columnIndex);

    int getInt(String columnLabel);

    int getInt(int columnIndex);

    boolean getBoolean(String columnLabel);

    boolean getBoolean(int columnIndex);

    byte getByte(String columnLabel);

    byte getByte(int columnIndex);

    byte[] getBytes(String columnLabel);

    byte[] getBytes(int columnIndex);
}
