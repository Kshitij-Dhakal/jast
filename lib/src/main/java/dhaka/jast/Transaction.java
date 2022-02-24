package dhaka.jast;

public interface Transaction {
    TransactionalSql sql(String sql);

    boolean elseCommit();
}
