# Project name: cinema-tickets-java

## Description
cinema-tickets-java validates ticket requests, makes payments and reserves seats for ticket purchasers.

## Prerequisites
- Java 17
- Maven
- Quarkus cli (if desire to run quarkus commands such as `quarkus build` or `quarkus dev`)
- Getting started with Quarkus can be found on this [starter page](https://quarkus.io/get-started/).

## Getting started
To build the project from project run either of the flowing commands
* `mvn clean verify`
* `quarkus build` (if Quarkus CLI is installed)

## Note
All solutions are implemented on `main` branch.
Added pipeline job to run `maven verify`. If unable to run please check your permissions

## Assumptions
In additon to the assumptions given on the exercise, The following assumptions were made since not explicitly given in the instructions
- No requirement to use vanilla Java in completing the exercise (Quarkus framework was used)
- No requirement to do money calculations with integers
- No requirement to add persistent layer
- Default log level assumed as `info` since it's not a production code
- The service is not called via endpoints, hence no REST resource or Global exception management is required 
