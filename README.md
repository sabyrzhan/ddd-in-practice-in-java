# Domain Driven Design in Practice in Java

Since I specialize in Java technology not in .NET, I implemented the source code 
in Java while learning Pluralsight's "Domain Driven Design in Practice" course https://www.pluralsight.com/courses/domain-driven-design-in-practice

The technologies I used in this repo:
- Java 8
- Spring Boot 2
- Maven
- JUnit 5
- Mockito 2
- PostgreSQL 11
- Flyway DB migration

Repository structure
---
The repository consists from the following maven modules:
- common - contains common/abstract DDD models
- core - contains main functionality and models where bounded contexts separated by packages
- web - UI of SnackMachine project
- webAtm - UI of ATM project
