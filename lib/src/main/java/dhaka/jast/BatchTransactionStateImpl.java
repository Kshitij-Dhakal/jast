package dhaka.jast;

import java.util.function.Predicate;

public class BatchTransactionStateImpl implements BatchTransactionState {
    @Override
    public BatchTransactionState bind(int i, String value) {
        return null;
    }

    @Override
    public BatchTransactionState bind(int i, long value) {
        return null;
    }

    @Override
    public BatchTransactionState bind(int i, int value) {
        return null;
    }

    @Override
    public BatchTransactionState bind(int i, boolean value) {
        return null;
    }

    @Override
    public BatchTransactionState bind(int i, byte value) {
        return null;
    }

    @Override
    public BatchTransactionState bind(int i, byte[] value) {
        return null;
    }

    @Override
    public Transaction rollbackIf(Predicate<Integer[]> i) {
        return null;
    }

    @Override
    public BatchTransactionState addBatch() {
        return null;
    }
}
