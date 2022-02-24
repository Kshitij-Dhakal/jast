package dhaka.example.blog;

import dhaka.jast.SqlRepo;

import javax.sql.DataSource;

class BlogRepo extends SqlRepo {
    BlogRepo(DataSource dataSource) {
        super(dataSource);
    }

    boolean transactionTest() {
        return begin().sql("sql1")
                .bind(1, "Some value")
                .rollbackIf(i -> i == 1)
                .sql("sql2")
                .bind(1, "Another Value")
                .rollbackIf(i -> i == 1)
                .elseCommit();
    }
}
