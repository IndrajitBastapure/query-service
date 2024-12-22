# Read Me First

**Query-Service API**

* Provide REST APIs to query IoT data with aggregations.

# Prerequisite - Environment Setup
1. JRE 17
2. Maven 4.0+
3. IDE of your choice
# Prerequisite - Dependency on another applications
* Query-Service API is dependent on 
  1. iot-simulator-service 
  2. data-ingestion-service
  
# Getting Started - Build & Run Application
* Build application with command - mvn clean install
* Start query-service application with command - mvn spring-boot:run
* Verify if the application started smoothly on port 8083
* Import Postman collection in Postman to test the query-service endpoints
* Before making call to actual application endpoints first generate Authorization(AccessToken) through auth0 endpoint - Get auth0 AccessToken
* Pass the generated accessToken as aAuthorization header while making call to the endpoints.
* Additionally, all the endpoint specification and documentation can be accessed from http://localhost:8083/swagger-ui/index.html

