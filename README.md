# event-manager

## Requirements

- Maven
- Java 8
- Mysql

### 1. Create database

```
create event_managerdb schema in your mysql manager
```

The app will update/create the DB once you run the application

## Project setup

```
mvn install
```

### Compiles and Run

```
mvn spring-boot:run
```

## Project Details

- Project is available at port 3005
- Change MySQL settings as needed in application.properties file, current settings connect to MySQL at 3306 with root user and password "root" at the "event_managerdb" schema
- Documentation of the API is available at localhost:{port}/swagger-ui/index.html
