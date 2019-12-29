# OAS API Validator #

This project is meant to validate JSON requests and responses against a known Open API Specification (OAS) API contract. The logic is based on Atlasian Swagger Request Validator framework: [![maven-central](https://maven-badges.herokuapp.com/maven-central/com.atlassian.oai/swagger-request-validator-core/badge.svg)](http://mvnrepository.com/artifact/com.atlassian.oai/swagger-request-validator-core)

The OAS validations implemented in this project are bassed on `com.atlassian.oai.validator.OpenApiInteractionValidator` which is instantiated with a local OAS file to validate the request and responses. The validator returns a `com.atlassian.oai.validator.report.ValidationReport` which will be analysed to pass or fail the tests


## Features ##

* The project exposes two different sets of unit tests:
** one designed to be independent of any HTTP library and validate the API responses stored as file resources
** the other based on spring-web `org.springframework.web.client.RestTemplate` which calls a remote service provider and validate the response against a OAS 3.0 API contract
* The project also implements a main method to run the validator standalone, see usage for more details


## Usage ##

See the [unit test](src/test/java/oas/api/validator/OpenAPIValidatorTest.java) for example on how the main method is used.

