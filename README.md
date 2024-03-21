## Spring keycloack Integration
# Key Features
- Single Sign-On(SSO) and single logout.
- Identity brokering and social login.
- User federation
- Fine-gained Authorization Services.
- Centralize management and admin console.
- Client Adapters.
- Standards-based(Oauth2)-

  ## Advantages of keycloack .
  - Open-source.
  - Versatility ->  Integration.
  - Scalability .
  - Security (bruteforce, password policies)-
  - Customisability .

  ## Keycloack Terms.
  - ``Realm``: way to isolate a set of users, roles and group policies, user registration etc. master ``realm``.
  - ``Clients``: Web applications, servers etc.
  - ``Client Scope ``: Optional client scopes, allows us to manage client specifications, eg emails, passwords etc.
  - ``Users``: Individuals that can authenticate with keycloack , found under a particular realm.
  - ``Groups``: A way to manage attributes, roles and groups for a particular users.

    ```
    http://localhost:8080/realms/Bansikah(User)/.well-known/openid-configuration
    ```

    # Configuration on postman
    ```
    grant_type:  password
    client_id : bansikah-rest-api
    username : bansikah
    password : bansikah
    ```

    visit [link](https://jwt.io)

Spring boot Configuration in application.yaml file

```
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/Bansikah  # Correct naming for realms
          jwk-set-uri: ${spring.security.oauth2.resourceserver.jwt.issuer-uri}/protocol/openid-connect/certs
server:
  port: 8081

jwt:
  auth:
    converter:
      resource-id: bansikah-rest-api
      principal-attribute: principal-name
```
    
