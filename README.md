# cl0ud
- 1 - Creating the application
- 2 - Simple CRUD
- 3 - DataBase connection - Did not work yet :)
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
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.3.Final</version>
</dependency>

...

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
```

- 5 - Testes unitarios
  - [Fixture Factory](https://github.com/six2six/fixture-factory)
- 6 - Redis