package dhaka.jast;

public interface Transaction {
    void commit();
    void rollback();
}
