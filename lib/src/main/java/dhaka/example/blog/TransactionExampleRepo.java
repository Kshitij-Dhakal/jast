package dhaka.example.blog;

import dhaka.jast.SqlRepo;

import javax.sql.DataSource;
import java.util.Arrays;

class TransactionExampleRepo extends SqlRepo {
    TransactionExampleRepo(DataSource dataSource) {
        super(dataSource);
    }

    boolean transactionTest() {
        return begin().sql("sql1")
                .bind(1, "Some value")
                .rollbackIf(i -> i == 1)
                .sql("sql2")
                .bind(1, "Another Value")
                .rollbackIf(i -> i == 1)
                .elseCommit()
                .exceptionally(throwable -> false);
    }

    boolean batchTransactionTest() {
        return begin().sql("batch sql")
                .bind(1, "V1")
                .addBatch()
                .bind(2, "V2")
                .addBatch()
                .bind(3, "V3")
                .addBatch()
                .rollbackIf(arr -> Arrays.stream(arr).anyMatch(i -> i != 1))
                .elseCommit()
                .exceptionally(throwable -> false);
    }
}
