# Demo Application to try out GraalVM AOT Image Java 17

## App Description
This is a demo app to test out graalvm ahead of time compilation. It consists of a crud functionalities
and an in-memory user for jwt authentication (Asymmetric).

## Pre-requisite
Docker installed

## Getting Started
* Validate app functionality by running tests ``mvn test``
* To convert to an image run ``mvn spring-boot:build-image``
* To convert to native image run ``mvn -Pnative spring-boot:build-image -DskipTests``

## Dependencies
* Spring web
* Spring jpa
* MySQL connector
* Lombok
* Flyway Migration
* spring-boot-starter-oauth2-resource-server
* Spring starter test
* Rest containers

## Important info
[native-image-libraries-and-frameworks](https://www.graalvm.org/native-image/libraries-and-frameworks/)