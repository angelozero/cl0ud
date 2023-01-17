# cl0ud
- 1 - Creating the application
- 2 - Simple CRUD
- 3 - DataBase connection - Did not work yet :)
    - How to resolve this issue *[Spring boot - Not a managed type](https://stackoverflow.com/questions/28664064/spring-boot-not-a-managed-type)* ?
    - Solution: *With Spring Version 6 and JDK 17 i got this problem i was using javax.persistent.Entity when i used [jakarta.persistent.Entity](https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api/3.1.0) this problem was resolved*