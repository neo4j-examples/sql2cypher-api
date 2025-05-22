# neo4j-jdbc-sql2cypher-api

This project is a proof of concept how to use the default translator of the [Neo4j JDBC Driver](https://github.com/neo4j/neo4j-jdbc) to translate SQL to Cypher.

Here are two possible example requests:

```http request
POST 127.0.0.1:8080/translate
Content-Type: text/plain

SELECT p, m FROM Person p
JOIN Movie m USING (ACTED_IN)
```

Or you can use a JSON body like this

```http request
POST 127.0.0.1:8080/translate
Content-Type: application/json

{
"sql": "SELECT name, born FROM People"
}
```

The application is not connected to a database, hence it has not any knowledge of a Neo4j database schema.
When used with the full JDBC driver, translation is more accurate and has additional features, please read more on our official documentation [SQL to Cypher translation](https://neo4j.com/docs/jdbc-manual/current/sql2cypher/).

## Requirements

Java 21+

## Running the application

```bash
./mvnw spring-boot:run
```

## Packaging the application

### As Java package

```bash
./mvnw clean verify
```

Run with

```bash
java -jar target/sql2cypher-api.jar 
```

### As native image

GraalVM 21+ is required

```bash
./mvnw native:compile -Pnative
```

Run with

```
./target/sql2cypher-api
```

More information in the [`HELP.md`](HELP.md).
