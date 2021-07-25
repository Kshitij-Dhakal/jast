package dhaka.example;

import dhaka.jast.SqlRepo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class PersonRepo extends SqlRepo {
    PersonRepo(DataSource dataSource) {
        super(dataSource);
    }

    Person save(Person person) throws FailedException {
        var errMsg = "Failed to save person.";
        var i = sql("INSERT INTO person(id, name, dp, created, deleted) VALUES(?, ?, ?, ?, ?, ?)")
                .bind(1, person.getId())
                .bind(2, person.getName())
                .bind(3, person.getDp())
                .bind(4, person.getCreated())
                .bind(5, person.getDeleted())
                .executeUpdate()
                .orElseThrow(failed(errMsg));
        if (i == 1) {
            return findPersonById(person.getId())
                    .orElseThrow();
        } else {
            throw new FailedException(errMsg);
        }
    }

    Optional<Person> findPersonById(String id) throws FailedException {
        return sql("SELECT * FROM person WHERE id=?")
                .bind(1, id)
                .withConverter(new PersonRowMapper())
                .findFirst()
                .orElseThrow(failed("Failed to get person by id."));
    }

    List<Person> findAllPerson() throws FailedException {
        return sql("SELECT * FROM person")
                .withConverter(new PersonRowMapper())
                .findAll()
                .orElseThrow(failed("Failed to get person list."));
    }

    private Function<Throwable, FailedException> failed(String errMsg) {
        return throwable -> new FailedException(errMsg, throwable);
    }
}
