# [Just Another SQL Template (JAST)](https://kshitij-dhakal.github.io/jast/)

JAST is a lightweight sql template library for java inspired
by [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc).

Javadoc link [here](https://kshitij-dhakal.github.io/jast/).

## Getting Started

Full code can be found inside `dhaka.example` package.

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
            .catchAndRethrow(failed(errMsg));
         if (i == 1) {
            return findById(person.getId())
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
      Optional<Person> findById(String id) throws FailedException {
         return sql("SELECT * FROM person WHERE id=?")
            .bind(1, id)
            .withConverter(new PersonRowMapper())
            .findFirst()
            .catchAndRethrow(failed("Failed to get person by id."));
      }
      
      List<Person> findAll() throws FailedException {
         return sql("SELECT * FROM person")
            .withConverter(new PersonRowMapper())
            .findAll()
            .catchAndRethrow(failed("Failed to get person list."));
      }
      
      private Function<Throwable, FailedException> failed(String errMsg) {
           return throwable -> new FailedException(errMsg, throwable);
      }
   }
   ```

## Transaction <sup>BETA</sup>

Execute transactions right from service layer. Jast will make all the update code inside the transaction block atomic
automatically.

```java
class BlogRepo extends SqlRepo {

    BlogRepo(DataSource dataSource) {
        super(dataSource);
    }

    boolean save(Blog blog) { /**/ }

    boolean delete(Blog blog) { /**/ }

    Optional<Blog> findById(String id) { /**/ }
}

class BlogService {
    private final BlogRepo blogRepo;

    public BlogService(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    Blog updateBlog(Blog blog) throws FailedException {
        return blogRepo
                .transactional(txn -> {
                    try {
                        if (blogRepo.delete(blog) &&
                                blogRepo.save(blog)) {
                            return commit(blog, txn);
                        } else {
                            return rollback(txn);
                        }
                    } catch (Throwable throwable) {
                        return rollback(txn);
                    }
                })
                .orElseThrow(() -> new FailedException("Failed to update blog."));
    }

    private Optional<Blog> commit(Blog blog, Transaction txn) {
        txn.commit();
        return blogRepo.findById(blog.getId());
    }

    private Optional<Blog> rollback(Transaction txn) {
        txn.rollback();
        return Optional.empty();
    }
}
```

__Note : Transaction API is work in progress and is subject to change later down the road.__

## Exception Handling <sup>BETA</sup>

JAST throws all checked exceptions sneakily using [jOOλ](https://github.com/jOOQ/jOOL). It is up to developer to handle
these exceptions.

* [The case against checked exceptions](https://stackoverflow.com/questions/613954/the-case-against-checked-exceptions)
* [Checked Exceptions are Evil](https://phauer.com/2015/checked-exceptions-are-evil/)

Developers can chain following method calls to handle exceptions.

1. Get the output of query execution and ignore exception.
   ```
   T get()
   ```

2. Throw an exception if it occurred, else return the output of the query execution.

   ```
   U rethrowError()
   ```

3. Map exception by the mapper function provided to get default value if exception occurs, else return the output of
   query execution.

   ```
   U exceptionally(Function<Throwable, T> fn)
   ```

4. Map exception by the mapper function provided to throw custom exception if exception occurs, else return the output
   of query execution.

   ```
   <E extends Throwable> U catchAndRethrow(Function<Throwable, E> fn) throws E
   ```

__Note : Exception handling API is work in progress and are subject to change later down the road.__

## Development

* [Building Java Libraries Sample](https://docs.gradle.org/current/samples/sample_building_java_libraries.html)
* [Publishing a project as module](https://docs.gradle.org/current/userguide/publishing_setup.html)