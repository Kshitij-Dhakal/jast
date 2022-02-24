package dhaka.jast;

import com.google.common.collect.Maps;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class TransactionImpl extends Transaction {
    private final DataSource dataSource;
    private final Map<TransactionalSql, IntPredicate> transactions = Maps.newHashMap();

    public TransactionImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public TransactionalSql sql(String query) {
        return new TransactionalSqlImpl(this, query);
    }

    @Override
    public UpdateResult<Boolean> elseCommit() {
        try (Connection con = dataSource.getConnection()) {
            con.setAutoCommit(false);
            for (Map.Entry<TransactionalSql, IntPredicate> entry : transactions.entrySet()) {
                if (!executeTransaction(con, entry)) {
                    return rollback(con);
                }
            }
            return commit(con);
        } catch (SQLException throwables) {
            return UpdateResult.error(throwables);
        }
    }

    private UpdateResult<Boolean> commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            return Unchecked.throwChecked(e);
        }
        return setAutoCommitTrue(con, true);
    }

    private boolean executeTransaction(Connection con, Map.Entry<TransactionalSql, IntPredicate> entry) {
        TransactionalSql transactionalSql = entry.getKey();
        var rollbackIf = entry.getValue();
        try (PreparedStatement pst = con.prepareStatement(transactionalSql.getQuery())) {
            for (Consumer<PreparedStatement> binder : transactionalSql.getBinders()) {
                binder.accept(pst);
            }
            return rollbackIf.test(pst.executeUpdate());
        } catch (SQLException throwables) {
            rollback(con);
            //noinspection ConstantConditions
            return Unchecked.throwChecked(throwables);
        }
    }

    private UpdateResult<Boolean> rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            return Unchecked.throwChecked(e);
        }
        return setAutoCommitTrue(con, false);
    }

    private UpdateResult<Boolean> setAutoCommitTrue(Connection con, boolean b) {
        try {
            con.setAutoCommit(true);
            return UpdateResult.of(b);
        } catch (SQLException e) {
            return Unchecked.throwChecked(e);
        }
    }

    @Override
    Transaction put(TransactionalSql sql, IntPredicate i) {
        this.transactions.put(sql, i);
        return this;
    }
}
