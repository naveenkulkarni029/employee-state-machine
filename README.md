# Employee State Machine

The repository helps develop Employee State Machine Server Application using Java 11, Spring Boot, H2 Database, maven build and run the application. This application Creates, Reads, Updates( The states of an Employee ) by exposing the Server-Side API's.

Below are the state transition for the email

1. ADDED: (DEFAULT State) When an Employee is created using the POST Endpoint
2. IN_CHECK: This state is transited via the ADDED state using the PUT Endpoint. Once the state gets changed to IN_CHECK, it can only go forward to the APPROVED state.
3. APPROVED: This state is transited via the IN_CHECK state using the PUT Endpoint. Once the state gets changed to APPROVED, it can either go one step forward (ACTIVE) or backward (IN_CHECK).
4. ACTIVE: This state is transited via the IN_CHECK state using the PUT Endpoint. THis is the last state of the Employee State Machine. Once it reaches the ACTIVE state there is no future state change for the employee.

- Each Employee has id, name, age, salary, emailId, mobileNumber, contractType, listOfEmployeeStateInformation and currentState.
- Each EmployeeStateInformation has id,  stateType, createdBy, and createdDateTime.
- The Employee is audit tracked with createdBy, created ( LocalDateTime ), modifiedBy and modified ( LocalDateTime ).
- The EmployeeStateInformation is audit tracked with createdBy and created ( LocalDateTime ).

## Pre-requsites

1. Java 11.
2. Maven 3.8+.
3. Spring Boot 2.5.7 (latest version).
4. Open API Spec using springdoc-openapi-ui 1.5.9
5. Spring Tool Suite IDE recommended.
6. Embedded H2 Database.
7. Docker Engine version 19.03.0+ ( Tested version: 20.10.8 ).
8. docker-compose version 1.29.2, build 5becea4c ( Tested ).

## Build and Run

### Steps to Build and Run using Maven

1. Download the project ( clone ) the project in the local machine.
2. Go to the project folder
3. Clean and install project using ``` mvn clean install ```
4. To run the project ``` mvn spring-boot:run```

### Build and Run using docker-compose

On your local machine, clone this repo and navigate to the folder backend/wallets. Then build and run the apllication following the [Docker Compose](https://docs.docker.com/compose/) documentation.

```bash
docker-compose up -d --build
```

> Docker Compose is included with [Docker Desktop](https://docs.docker.com/desktop/).
> If you don't have Docker Compose installed, [follow these installation instructions](https://docs.docker.com/compose/install/).

To rebuild the application after you made changes, run the `docker-compose up` command
again. This rebuilds the application, and updates the container with your changes:

```bash
docker-compose up -d --build
```

To stop the containers, use the `docker-compose down` command:

```bash
docker-compose down
```
## Access the API using Swagger

Click on the link to access the swagger for the application [Employee-State-Machine-swagger](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config)

## Access the H2 Database

Click on the link to access the swagger for the application [Employee-State-Machine-server-H2-Console](http://localhost:8080/h2-console)

Click on Connect to access the DB.

if the DB connection fails with an error. Validate if the password field is kept `empty` and JDBC URL is set to
```
jdbc:h2:mem:testdb
```
