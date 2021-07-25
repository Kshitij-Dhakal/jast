# Just Another SQL Template (JAST)

JAST is a lightweight sql template library for java that is inspired
by [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc). It uses [jOOλ](https://github.com/jOOQ/jOOL) throw
checked exception.

## Getting Started

Full code can be found inside dhaka.jast.example package.

1. Creating SQLRepo

    ```java
    class PersonRepo extends SqlRepo {
        PersonRepo(DataSource dataSource) {
            super(dataSource);
        }
    }
    ```
2. Running update query

   ```java
   class PersonRepo extends SqlRepo {
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
   }
   ```

3. Creating row mapper

   ```java
   class PersonRowMapper implements RowMapper<Person> {
       @Override
       public Person map(Row row) {
           var person = new Person();
           person.setId(row.getString("id"));
           person.setName(row.getString("name"));
           person.setDp(row.getString("dp"));
           person.setCreated(row.getLong("created"));
           person.setDeleted(row.getBoolean("deleted"));
           return person;
       }
   }
   ```

3. Getting data from database

   ```java
   class PersonRepo extends SqlRepo {
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
   ```

## Exception Handling

JAST relies on [jOOλ](https://github.com/jOOQ/jOOL) to throw all checked exception sneakily. It is up to developer to
handle these exceptions.

* [The case against checked exceptions](https://stackoverflow.com/questions/613954/the-case-against-checked-exceptions)
* [Checked Exceptions are Evil](https://phauer.com/2015/checked-exceptions-are-evil/)

Developers have the option to call `.orElseThrow((T) => E) throws E` to map internal exception into custom
exception and throw it instead. E can be both checked or runtime exception.

## Development

* [Building Java Libraries Sample](https://docs.gradle.org/current/samples/sample_building_java_libraries.html)
* [Publishing a project as module](https://docs.gradle.org/current/userguide/publishing_setup.html)