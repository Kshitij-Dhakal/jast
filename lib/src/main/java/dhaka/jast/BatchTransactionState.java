package dhaka.jast;

import java.util.function.Predicate;

public interface BatchTransactionState extends Sql {
    @Override
    BatchTransactionState bind(int i, String value);

    @Override
    BatchTransactionState bind(int i, long value);

    @Override
    BatchTransactionState bind(int i, int value);

    @Override
    BatchTransactionState bind(int i, boolean value);

    @Override
    BatchTransactionState bind(int i, byte value);

    @Override
    BatchTransactionState bind(int i, byte[] value);

    Transaction rollbackIf(Predicate<Integer[]> i);
}
