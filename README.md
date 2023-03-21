# cl0ud
- *Obs: First time running the app ? Don't forget to run first*
  - `mvn flyway:repair`
  - `mvn flyway:migrate`
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

- 5 - Unity Tests
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

---

- 7 - Swagger
  - To access: `http://localhost:8080/swagger-ui/index.html`

  - ![swagger_image](https://i.postimg.cc/fWVX1RSY/Screen-Shot-2023-03-20-at-21-41-17.png)

  - Some documentations to see how to fix some issues and configure swagger
    - [Video - How to update environment variables based on a response in Postman | Chaining Requests](https://www.youtube.com/watch?v=wHuSu_Mf6Hs)
    - [Stackoverflow - How to configure Swagger info](https://stackoverflow.com/questions/72037222/springdoc-openapi-ui-swagger-3-change-api-description)
    - https://springdoc.org/v2/
    - https://github.com/springdoc/springdoc-openapi/issues/1284
    - https://stackoverflow.com/questions/71549614/springfox-type-javax-servlet-http-httpservletrequest-not-present


```xml
<!-- Swagger to Spring 3-->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.2</version>
</dependency>
<!--Fix the error after put the swagger dependency -->
<!-- Unable to create a Configuration, because no Jakarta Bean Validation provider could be found. -->
<!-- Add a provider like Hibernate Validator (RI) to your classpath. -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

- **ERROR**: `Type javax.servlet.http.HttpServletRequest not present`
    - If you are using springfox dependency just remove it.

```xml
<!-- REMOVE THIS DEPENDENCY !!! -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>ANY_VERSION</version>
</dependency>
```

- **OBS!!!**
  - Spring 3 dosen't have suport to jakarta EE ! See more in [Add support for Jakarta EE](https://github.com/springdoc/springdoc-openapi/issues/1284)

- Class configuration

```javascript

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerOpenAPIConfig {

    @Bean
    public OpenAPI setup() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cl0ud API")
                        .description("REST API simple application")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github - angelozero")
                        .url("https://github.com/angelozero/cl0ud"));
    }
}
```

---

- 8 - Integration tests with
  - 8.1 - [Test Contaiener](https://www.testcontainers.org/)
  - 8.2 - [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html)

- 9 - JWT
  - [How to convert a SQL file to a PostgreSQL ?](http://www.sqlines.com/online) 
  - [Generating a KEY](https://www.allkeysgenerator.com/)

---

- XX - Redis, when ?  I dont't know :)

-- Follow the [Trello](https://trello.com/invite/b/wIilDAIF/ATTI2a1001727d2ee9f8bd0f5495d34f05588107B7E7/cl0ud-app) from this projetc.

-- changes