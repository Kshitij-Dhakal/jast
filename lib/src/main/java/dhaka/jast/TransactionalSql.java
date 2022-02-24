package dhaka.jast;

import java.util.function.IntPredicate;

public interface TransactionalSql extends Sql {
    @Override
    TransactionalSql bind(int i, String value);

    @Override
    TransactionalSql bind(int i, long value);

    @Override
    TransactionalSql bind(int i, int value);

    @Override
    TransactionalSql bind(int i, boolean value);

    @Override
    TransactionalSql bind(int i, byte value);

    @Override
    TransactionalSql bind(int i, byte[] value);

    Transaction rollbackIf(IntPredicate i);

    BatchTransactionState addBatch();
}
