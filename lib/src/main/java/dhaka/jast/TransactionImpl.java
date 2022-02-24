package dhaka.jast;

public class TransactionImpl implements Transaction {
    @Override
    public TransactionalSql sql(String sql) {
        return null;
    }

    @Override
    public boolean elseCommit() {
        return false;
    }
}
