package dhaka.jast;

import java.util.function.IntPredicate;

public class TransactionalSqlImpl implements TransactionalSql {
    @Override
    public TransactionalSql bind(int i, String value) {
        return null;
    }

    @Override
    public TransactionalSql bind(int i, long value) {
        return null;
    }

    @Override
    public TransactionalSql bind(int i, int value) {
        return null;
    }

    @Override
    public TransactionalSql bind(int i, boolean value) {
        return null;
    }

    @Override
    public TransactionalSql bind(int i, byte value) {
        return null;
    }

    @Override
    public TransactionalSql bind(int i, byte[] value) {
        return null;
    }

    @Override
    public Transaction rollbackIf(IntPredicate i) {
        return null;
    }

    @Override
    public BatchTransactionState addBatch() {
        return null;
    }
}
