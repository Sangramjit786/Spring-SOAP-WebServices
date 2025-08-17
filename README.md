# Spring SOAP Web Services

This project demonstrates the implementation of SOAP web services using Spring Boot. It provides course management functionality through SOAP-based web services.

## Project Overview

This project implements a SOAP web service for managing course details with the following operations:
- Get course details by ID
- Get all courses
- Delete a course by ID

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── soap/
│   │           └── webservices/
│   │               ├── endpoint/
│   │               │   ├── CourseDetailsEndpoint.java  # SOAP endpoint implementation
│   │               │   ├── WebServiceConfig.java       # Spring WS configuration
│   │               │   └── bean/                       # Domain model
│   │               │       └── Course.java
│   │               └── service/
│   │                   └── CourseDetailsService.java   # Business logic
│   └── resources/
│       ├── application.properties
│       └── course-details.xsd                          # XSD schema for SOAP messages
```

## Implementation Details

### 1. SOAP API Implementation

The project implements a SOAP-based web service with the following endpoints:
- `GetCourseDetailsRequest` - Get details of a specific course by ID
- `GetAllCourseDetailsRequest` - Get details of all available courses
- `DeleteCourseDetailsRequest` - Delete a course by ID

The service uses JAXB for XML binding and Spring-WS for SOAP web services.

### 2. Web Service Configuration

The `WebServiceConfig` class configures the SOAP web service with:
- Message Dispatcher Servlet for handling SOAP requests
- WSDL generation based on the XSD schema
- Automatic endpoint mapping

```java
@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    
    @Bean(name = "courses")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CoursePort");
        definition.setTargetNamespace("http://java-soap.com/courses");
        definition.setLocationUri("/ws");
        definition.setSchema(coursesSchema);
        return definition;
    }
}
```

### 3. Message Dispatcher Servlet

The Message Dispatcher Servlet is configured to handle all requests to `/ws/*` and route them to the appropriate endpoint methods based on the SOAP action and payload root element.
- `MessageDispatcherServlet` is the core of Spring Web Services.  
- It is configured in `WebConfig` to:
  - Handle SOAP messages.  
  - Map requests to correct endpoints.  
  - Expose WSDL URL.

### 4. WSDL Generation

The WSDL is automatically generated from the XSD schema (`course-details.xsd`) and exposed at:
```
http://localhost:8080/ws/courses.wsdl
```

The XSD defines the following elements:
- `GetCourseDetailsRequest/Response`
- `GetAllCourseDetailsRequest/Response`
- `DeleteCourseDetailsRequest/Response`
- `CourseDetails` type with id, name, and description fields

### 5. Get All Course Details Implementation

The `GetAllCourseDetailsRequest` endpoint retrieves all available courses:

```java
@PayloadRoot(namespace = "http://java-soap.com/courses", 
            localPart = "GetAllCourseDetailsRequest")
@ResponsePayload
public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
        @RequestPayload GetAllCourseDetailsRequest request) {
    List courses = service.findAll();
    return mapAllCourseDetails(courses);
}
```

### 6. Delete Course Implementation

The `DeleteCourseDetailsRequest` endpoint deletes a course by ID:

```java
@PayloadRoot(namespace = "http://java-soap.com/courses", 
            localPart = "DeleteCourseDetailsRequest")
@ResponsePayload
public DeleteCourseDetailsResponse deleteCourseDetailsRequest(
        @RequestPayload DeleteCourseDetailsRequest request) {
    Status status = service.deleteById(request.getId());
    DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
    response.setStatus(mapStatus(status));
    return response;
}
```

### 7) Implementing SOAP Web Service – GetAllCourseDetailsRequest

- Endpoint created to handle GetAllCourseDetailsRequest.
- Returns a list of available courses with details.
- Example request:

```xml
<GetAllCourseDetailsRequest xmlns="http://springsoapwebservices.com/courses"/>
```

- Example response:
```xml
<GetAllCourseDetailsResponse>
    <CourseDetails>
        <id>1</id>
        <name>Spring Boot</name>
        <description>Microservices with Spring Boot</description>
    </CourseDetails>
</GetAllCourseDetailsResponse>
```

### 8) Implementing SOAP Web Service – DeleteCourseDetailsRequest

- Endpoint created to handle DeleteCourseDetailsRequest.
- Deletes a course by ID and returns status (SUCCESS/FAILURE).
- Example request:
```xml
<DeleteCourseDetailsRequest xmlns="http://springsoapwebservices.com/courses">
    <id>1</id>
</DeleteCourseDetailsRequest>
```
- Example response:
```xml
<DeleteCourseDetailsResponse>
    <status>SUCCESS</status>
</DeleteCourseDetailsResponse>
```

## Running the Application

1. Clone the repository
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`
4. Access the WSDL at: `http://localhost:8080/ws/courses.wsdl`

## Testing the Web Service

You can test the web service using SOAP UI or any SOAP client. Example request/response pairs are provided in the `example-files` directory.

## Dependencies

- Spring Boot Starter Web Services
- Spring Web
- Spring Boot DevTools
- Lombok (for reducing boilerplate code)
- JAXB API (for XML binding)

## ✅ Benefits of This Project

- Hands-on demonstration of building SOAP Web Services with Spring.
- Dynamic WSDL generation from XSD schema.
- Proper separation of service layer and endpoints.
- Implements multiple SOAP operations for CRUD-like functionality.

## 🔗 References

- Spring Web Services Docs
- SOAP vs REST
