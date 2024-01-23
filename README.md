### What is this example about?

This example shows how to use PostgreSQL array operators in Spring Data JPA Specification

### Why this example exists?

I was looking for a way to use PostgreSQL array operators in Spring Data JPA Specification and I didn't find any
examples of it, so I decided to create this example

### How it works?

This example uses `cb.function()` call to call PostgreSQL array operators
and `PostgreSQLOperatorsAdditionalFunctionsContributor` class to register additional functions in Hibernate
,so it's possible to use them in `cb.function()` call

### Example of usage

```java
public class PostService {

    // ...

    private Specification<Post> hasTags(List<String> tags) {
        return (root, query, cb) -> {
            String arrayAsString = "{" + String.join(",", tags) + "}";
            return cb.isTrue(cb.function("@>", Boolean.class, root.get("tags"), cb.literal(arrayAsString)));
        };
    }

    // ...
}
```

### Prerequisites to run

- Java 17
- Gradle 8.5
- PostgreSQL 
- Docker (optional for running PostgreSQL in Docker for tests)

### How to run

- Create database named `postgres-array-demo` in your PostgreSQL instance
- if you have different database name/username/password/port, please change it in `application.yml` file
- Run `./gradlew bootRun` to start the application
- Open your browser and go to [http://localhost:8080/posts](http://localhost:8080/posts) with different query parameters
  to see the result:
    - if [no query parameters](http://localhost:8080/posts) are specified, all posts will be returned
    - [`?tags=tag1`](http://localhost:8080/posts?tags=tqg1) - find posts with tag `tag1`
    - [`?tags=tag1,tag2`](http://localhost:8080/posts?tags=tag1,tag2) - find posts with tag `tag1` and `tag2`
    - And so on...

### Questions and Answers

#### Is It safe to use this approach in production?

Dunno, It's still possible to use SQL injection (probably) depending on how you use it, but it's better than nothing

#### Is It possible to use this approach with other databases?

Yes, if your database uses the same syntax for array operators as PostgreSQL or you can change this
in `PostgreSQLOperatorsAdditionalFunctionsContributor` class

#### Is It possible to use this approach with other ORMs?

No, since this approach is based on registering additional functions in Hibernate

#### Why don't you use `ParameterExpression` in specification?

Because it's not possible to use it with cb.function() call and pass value for it since CriteriaQuery doesn't support
setting parameters for functions and Spring Data JPA doesn't support it too (as far as I know)

#### Why don't you use `cb.literal()` directly in specification?

Because it's not possible to call cb.function() and pass value as array since both Hibernate and JPA don't support it.
So, I have to convert array to string and pass it as string literal to cb.function() call.
Not sure if its best approach, but it works

