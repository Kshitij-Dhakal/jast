package dhaka.jast;

public interface TransactionBlock<U, E extends Throwable> {
    U begin(Transaction txn) throws E;
}
