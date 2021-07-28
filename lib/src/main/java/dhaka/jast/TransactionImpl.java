package dhaka.jast;

import java.sql.Connection;
import java.sql.SQLException;

import static dhaka.jast.JastCommons.throwChecked;

class TransactionImpl implements Transaction {
    private final Connection connection;

    TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void commit() {
        try {
            if (connection != null) {
                connection.commit();
            }
        } catch (SQLException throwable) {
            //noinspection ResultOfMethodCallIgnored
            throwChecked(throwable);
        }
    }

    @Override
    public void rollback() {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException throwable) {
            //noinspection ResultOfMethodCallIgnored
            throwChecked(throwable);
        }
    }
}
