# cl0ud
- *Obs: First time running the app ? Don't forget to run first `mvn flyway:migrate`*
---
- 1 - Creating the application
- 2 - Simple CRUD
- 3 - DataBase connection
  - How to resolve this issue *[Spring boot - Not a managed type](https://stackoverflow.com/questions/28664064/spring-boot-not-a-managed-type)* ?
  - Solution: *With Spring Version 6 and JDK 17 I got this problem when I was using javax.persistent.Entity. When I used [jakarta.persistent.Entity](https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api/3.1.0) this problem was resolved*
- 4 - Using MapStruct with Lombok
  - [Mapstruc Documentation](https://mapstruct.org/documentation/installation/)
  - [Baeldung - Quick Guide to MapStruct](https://www.baeldung.com/mapstruct)
  - [Stackoverflow - MapStruct and Lombok not working together](https://stackoverflow.com/questions/47676369/mapstruct-and-lombok-not-working-together)
  - [Mvn Repository - Project Lombok](https://mvnrepository.com/artifact/org.projectlombok/lombok/1.18.22)
```java
  import org.mapstruct.Mapper;

  @Mapper(componentModel = "spring")
  public interface PersonMapper {
      Person toModel(PersonEntity personEntity);

      PersonEntity toEntity(Person person);
  }
```
```xml
<!-- https://mvnrepository.com/artifact/org.mapstruct/mapstruct -->
<pom.xml>
  <dependency>
      <groupId>org.mapstruct</groupId>
      <artifactId>mapstruct</artifactId>
      <version>1.5.3.Final</version>
  </dependency>
  
  <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.8.1</version>
      <configuration>
          <source>17</source>
          <target>17</target>
          <!-- https://www.baeldung.com/mapstruct -->
          <annotationProcessorPaths>
              <path>
                  <groupId>org.projectlombok</groupId>
                  <artifactId>lombok</artifactId>
                  <version>${org.projectlombok.version}</version>
              </path>
              
              <!-- This is needed when using Lombok 1.18.16 and above -->
              <path>
                  <groupId>org.projectlombok</groupId>
                  <artifactId>lombok-mapstruct-binding</artifactId>
                  <version>0.2.0</version>
              </path>
              
              <path>
                  <groupId>org.mapstruct</groupId>
                  <artifactId>mapstruct-processor</artifactId>
                  <version>${org.mapstruct.version}</version>
              </path>
          </annotationProcessorPaths>
      </configuration>
  </plugin>
</pom.xml>
```

- 5 - Testes unitarios
  - [Fixture Factory](https://github.com/six2six/fixture-factory)

- 6 - FlyWay
  - Are you running for the first time the app ? -> Command to create and populate the tables `mvn flyway:migrate`
  - Things that I have to do 
  - Update my postegsql version to 14
    - [Pt.01 - Removing all the posgres using brew](https://stackoverflow.com/questions/51992362/completely-remove-postgres-after-brew-uninstall-osx)
    - [Pt.02 - Go to -> /Library/PostgreSQL/POSTGRES_VERSION/uninstall-postgresql.app](https://nektony.com/how-to/uninstall-postgresql-on-mac)
    - [Installing PostgreSQL 14 0 using brew](https://formulae.brew.sh/formula/postgresql@14)
    - [Download PgAdmin 4 - pgAdmin 4 v6.15 (released Oct. 20, 2022)](https://www.pgadmin.org/download/pgadmin-4-macos/)
  - [Configure the brew Postgresql](https://www.sqlshack.com/setting-up-a-postgresql-database-on-mac/)
    - on terminal type to enter into posgres terminal
      ```shell
      psql postgres
      ```
    - [create a database](https://pgdocptbr.sourceforge.io/pg80/sql-createdatabase.html) with the command
      ```shell
      CREATE DATABASE your_data_base_name;
      ```
  - [Configuring Flyway using flyway.conf file](https://www.baeldung.com/database-migrations-with-flyway)
    - Create in the $PROJECT_ROOT the flyway.conf file
    ```yml
    flyway.user=databaseUser
    flyway.password=databasePassword
    flyway.schemas=app-db
    flyway.url=jdbc:h2:mem:DATABASE
    flyway.locations=filesystem:db/migration
    ```
    - Don't forget it to add this configs into your application.yml
    ```yml
    jpa:
    hibernate:
      ddl-auto: update # When you launch the application for the first time - switch "none" at "create"
    show-sql: true
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    properties:
      javax:
        persistence:
          schema-generation:
            scripts:
              action: create
              create-target: create.sql
              create-source: metadata
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        globally_quoted_identifiers: true
    open-in-view: false
    generate-ddl: true
    ```
  - *IMPORTANT! Here are the supported versions from Flyway to PostgresSQL ( [this project use version 14](https://flywaydb.org/documentation/database/postgresql) )*
    ![versions](https://i.postimg.cc/m2cFk21b/Screen-Shot-2023-02-04-at-20-54-37.png)

- 
- XX - Redis, when ?  I dont't know :)