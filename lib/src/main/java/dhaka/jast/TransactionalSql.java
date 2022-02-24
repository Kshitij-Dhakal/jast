package dhaka.jast;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public abstract class TransactionalSql implements Sql {
    @Override
    public abstract TransactionalSql bind(int i, String value);

    @Override
    public abstract TransactionalSql bind(int i, long value);

    @Override
    public abstract TransactionalSql bind(int i, int value);

    @Override
    public abstract TransactionalSql bind(int i, boolean value);

    @Override
    public abstract TransactionalSql bind(int i, byte value);

    @Override
    public abstract TransactionalSql bind(int i, byte[] value);

    public abstract BatchTransactionState addBatch();

    public abstract Transaction rollbackIf(IntPredicate i);

    abstract List<Consumer<PreparedStatement>> getBinders();

    abstract String getQuery();
}
