package dhaka.jast;

import java.util.function.IntPredicate;

public abstract class Transaction {
    public abstract TransactionalSql sql(String sql);

    public abstract UpdateResult<Boolean> elseCommit();

    abstract Transaction put(TransactionalSql sql, IntPredicate i);
}
