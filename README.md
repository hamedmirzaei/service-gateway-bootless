# Spring MVC + Spring Data + Apache Camel (Service Gateway Using Eureka)

In this demo you see how we can use Apache Camel alongside Eureka Service Discovery in order
to implement a service gateway in a Spring MVC environment (Boot-less). 
You are able to map eureka services with custom context paths and relegate the 
requests come to camel, to the appropriate eureka service.
So, basically you just know about camel and are able to send requests to it and in the back-end, camel
relegate your request to the appropriate eureka-service and then return the response to you.

## Libraries and Tools
* [Framework] `Spring MVC`
* [Integration Framework] [`Apache Camel`](https://camel.apache.org/) which uses Spark Java for REST API
* [ORM] `Hibernate` under abstraction of [`Spring Data JPA`](https://spring.io/projects/spring-data-jpa)
* [Database] `Oracle on port 1521`

## Prerequisite

In order to test this demo you should already have the followings:
* An Oracle database connection and consequently changing `database.properties` for it. In addition you should
update `enums/Schema.java` file with your schema name

* An `Eureka` server starting for registry/discovery of services. In this case you can use other repository 
of mine named `service-gateway` and just start `config-server`, `eureka-server` and `account-service` 
modules in order. This will start an Eureka server on `localhost:8090` and registers a REST service 
named `account-service` on it. In case you want to change the Eureka port, you should 
update `eureka.properties` file with your own configs.

* Port `8089` should be open for camel REST API. You are able to configure it in `application.properties` file.

## How to run

You need to run this demo on a tomcat server. For the rest of the article lets assume this demo is 
started on `localhost:8082`.

## How it works

* To get all the services: `Http.GET` to `http://localhost:8082/service-gateway/services`
* To add a mapping for a pair of context path and eureka service: `Http.POST` to `http://localhost:8082/service-gateway/services`
with the following json as request body:

```
{
    "contextPath": "accounts",
    "eurekaServiceName": "account-service",
    "eurekaServiceStatusEntity": {
        "id": 1,
        "eurekaServiceStatusType": "PUBLISHED",
        "description": "published status"
    },
    "subSystemCategoryEntity": {
        "id": 1,
        "subSystemCategoryType": "ARZI",
        "description": "arzi category"
    }
}
```

After adding the mapping of `accounts` context path for `account-service` eureka service, you are able
to call camel with `Http.GET` on `http://localhost:8089/services/accounts` to get the list of accounts
comes from `account-service` eureka service. 