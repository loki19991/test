Customer Profiel API: This is REST API to create, modify and delete user profile.

User REST resource is protected by Authorization server which does authenticartion
and authorization by oauth2 security mechanism(grant_type=client_credentials).
This API uses in memory H2 database for storage.

to run application, please follow below instructions<br>
mvn clean install<br>
mvn spring-boot:run

The following url is link to Swagger doc and authentication endpoint:<br>
http://localhost:8080/swagger-ui.html#/<br>

The authentication endpoints:
http://localhost:8080/oauth/token<br>
authServer-sample-client:<br>
  clientId: lR9kYCOVzMa4iTuMPIsF9ePmtz8a<br>
  clientSecret: 08oKwEJckE3D7HNxy3f9W0B6XL8a<br>
  scope: read:profile, write:profile<br>

The REST endpoints:<br>
POST: http://localhost:8080/api/v1/users<br>
GET: http://localhost:8080/api/v1/users/{id}/profile<br>
PUT: http://localhost:8080/api/v1/users/{id}/profile<br>
DELETE: http://localhost:8080/api/v1/users/{id}<br>

Create User PayLoad: <br>
```
{
  "profile": {
    "addressList": [
      {
        "country": "string",
        "emailAddress": "string",
        "state": "string",
        "streetAddress": "string",
        "suburb": "string",
        "type": "WORK"
      }
    ],
    "dateOfBirth": "1991-04-16",
    "firstName": "string",
    "lastName": "string"
  }
}
