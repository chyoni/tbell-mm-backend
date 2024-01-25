# TBELL Man/Month Backend

## Docker DB Container commands
```shell
docker exec -it tbell-mm-db bash
psql -U cwchoiit -d mm
```

## Environments
    - Gradle 
    - Spring Boot V3.2.2
    - Spring Data JPA
    - QueryDSL V5.0.0
    - H2 Database (Test DB)
    - PostgreSQL V13.1
    - Docker Container 
        - Backend
        - DB
        - DB Backup
    - Docker Compose 

## QueryDSL Q File Build
```shell
./gradlew bootJar
```
- 위 커맨드 실행 시 src/main/generated 경로에 Q File 생성 확인