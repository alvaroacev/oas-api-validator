# OAS API Validator #

This project is meant to validate JSON requests and responses against a known Open API Specification (OAS) API contract. The logic is based on [Atlasian Swagger Request Validator framework](https://bitbucket.org/atlassian/swagger-request-validator/src/master/)

Requests and responses validations implemented are bassed on `com.atlassian.oai.validator.OpenApiInteractionValidator` which is instantiated with a local OAS file. The validator returns a `com.atlassian.oai.validator.report.ValidationReport` which is inspected to determine success or failure.

## Features ##

* The project exposes two different sets of unit tests:
** one designed to be independent of any HTTP library and validate the API responses stored as file resources
** the other based on spring-web `org.springframework.web.client.RestTemplate` which calls a remote service provider and validate the response against a OAS 3.0 API contract
* The project also implements a main method to run the validator standalone, see usage for more details


## Usage ##

See [unit test](src/test/java/oas/api/validator/OpenAPIValidatorTest.java) for how the main method is used. The main expects following arguments:

```shell
usage: Open API validator
 -apiPath <apiPath>         HTTP method (e.g. /payments/{id}/transactions).
 -httpMethod <httpMethod>   HTTP method (e.g. GET, PUT, POST, DELETE.
 -oas <oas>                 Open API Specification URI in YAML format.
 -request <request>         HTTP request URI in JSON formart.
 -response <response>       HTTP response URI in JSON formart.
 ```
 
From the command line you can run the project by using following command:

```shell
java -cp open-api-validator.jar oas.api.validator.OpenAPIValidator -oas ./api-contract.yaml -response ./response.json -apiPath /user/status -httpMethod GET
```
