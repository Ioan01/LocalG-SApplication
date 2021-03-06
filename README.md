LocalG&SApplication

# **Mock Goods and Services Marketplace Application**

## _Introduction_

The project is currently under construction. Most new features are scattered between different development branches. Currently, main only features stable and final functionalities.

This is only the backend providing the REST api for the project. The frontend consuming this API is found [here](https://github.com/Ioan01/G-SFrontend)

This application is an exercise for the FIS laboratory. You can find the lab assignment on the [lab web page](http://labs.cs.upt.ro/~oose/pmwiki.php/FSE/LAB2019).



## Java Spring

>Spring Framework is a Java platform that provides comprehensive infrastructure support for developing Java applications. Spring handles the infrastructure so you can focus on your application.

>Spring enables you to build applications from “plain old Java objects” (POJOs) and to apply enterprise services non-invasively to POJOs. This capability applies to the Java SE programming model and to full and partial Java EE.




## Features
 - Persistent data stored on a MongoDB cluster
 - Account creation / deletion
 - Product addition
 - Store viewing and filtering
 - Image uniqueness based on the image hashCode

## Technologies used
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Digital Ocean - for deployment](https://www.digitalocean.com/)
- [JWT authorization](https://jwt.io/)
- [Mongodb - database](https://www.mongodb.com/)
- [Maven](https://maven.apache.org/)

# Deployment

For deployment the following files need to be created in backend/src/main/resources

#### application.properties 

```

spring.data.mongodb.uri=mongodb+srv://<user>:<password>@server

spring.data.mongodb.auto-index-creation=true

spring.profiles.active = development

server.error.include-message=always

security.jwt.secretKey = <256-byte key>

security.jwt.issuer = <your issuer>
```
#### application.yml

```
spring:
  config:
    activate:
      on-profile: development

environment: development
minPassword : 1
minUser : 1
maxUser : 100
maxPassword : 100

roles : client, provider

---
spring:
  config:
    activate:
      on-profile: production

environment: production
minPassword : 12
minUser : 6
maxUser : 20
maxPassword : 20
```

#### To generate a working jar : 


```
cd backend
mvn clean package
cd target
java -jar <jarFile>
```

## REST api documentation
- [Postman link]()

## Libraries used
