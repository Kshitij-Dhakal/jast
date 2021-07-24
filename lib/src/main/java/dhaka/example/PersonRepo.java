package dhaka.example;

import dhaka.jast.SqlRepo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

class PersonRepo extends SqlRepo {
    PersonRepo(DataSource dataSource) {
        super(dataSource);
    }

    Person save(Person person) {
        var i = sql("INSERT INTO person(id, name, dp, created, deleted) VALUES(?, ?, ?, ?, ?, ?)")
                .bind(1, person.getId())
                .bind(2, person.getName())
                .bind(3, person.getDp())
                .bind(4, person.getCreated())
                .bind(5, person.getDeleted())
                .executeUpdate();
        if (i == 1) {
            return findPersonById(person.getId())
                    .orElseThrow();
        } else {
            throw new FailedException("Failed to save person.");
        }
    }

    Optional<Person> findPersonById(String id) {
        return sql("SELECT * FROM person WHERE id=?")
                .bind(1, id)
                .withConverter(new PersonRowMapper())
                .findFirst();
    }

    List<Person> findAllPerson() {
        return sql("SELECT * FROM person")
                .withConverter(new PersonRowMapper())
                .findAll();
    }
}
