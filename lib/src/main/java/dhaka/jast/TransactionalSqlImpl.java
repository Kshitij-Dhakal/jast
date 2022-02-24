package dhaka.jast;

import com.google.common.collect.Lists;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

import static dhaka.jast.Unchecked.consume;

public class TransactionalSqlImpl extends TransactionalSql {
    private final Transaction transaction;
    private final String query;
    private final List<Consumer<PreparedStatement>> binders = Lists.newArrayList();

    public TransactionalSqlImpl(Transaction transaction, String query) {
        this.transaction = transaction;
        this.query = query;
    }

    @Override
    public TransactionalSql bind(int i, String value) {
        return bind(consume(ps -> ps.setString(i, value)));
    }

    @Override
    public TransactionalSql bind(int i, long value) {
        return bind(consume(ps -> ps.setLong(i, value)));
    }

    @Override
    public TransactionalSql bind(int i, int value) {
        return bind(consume(ps -> ps.setLong(i, value)));
    }

    @Override
    public TransactionalSql bind(int i, boolean value) {
        return bind(consume(ps -> ps.setBoolean(i, value)));
    }

    @Override
    public TransactionalSql bind(int i, byte value) {
        return bind(consume(ps -> ps.setByte(i, value)));
    }

    @Override
    public TransactionalSql bind(int i, byte[] value) {
        return bind(consume(ps -> ps.setBytes(i, value)));
    }

    @Override
    public BatchTransactionState addBatch() {
        //TODO: handle add batch
        return null;
    }

    @Override
    public Transaction rollbackIf(IntPredicate i) {
        return this.transaction.put(this, i);
    }

    @Override
    List<Consumer<PreparedStatement>> getBinders() {
        return this.binders;
    }

    @Override
    String getQuery() {
        return this.query;
    }

    private TransactionalSql bind(Consumer<PreparedStatement> consumer) {
        binders.add(consumer);
        return this;
    }
}
