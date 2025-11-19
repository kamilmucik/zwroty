mvn clean compile flyway:clean flyway:repair 
mvn flyway:migrate mvn flyway:info


```bash
mvn -pl :db-unit clean compile flyway:clean flyway:repair
```

```bash
mvn -pl :db-unit flyway:info 
```
```bash
mvn -pl :db-unit flyway:migrate 
```
