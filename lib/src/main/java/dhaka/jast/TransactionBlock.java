package dhaka.jast;

public interface TransactionBlock<T extends SqlRepo, U> {
    U begin(T repo) throws Throwable;
}
