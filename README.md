# Custom Keycloak Authenticator SPI

This project provides a minimal Keycloak Authenticator SPI that logs a message and allows the authentication flow to continue.

## Project structure

```
custom-authenticator/
  pom.xml
  docker-compose.yml
  src/
    main/
      java/
        com/example/keycloak/spi/
          CustomAuthenticator.java
          CustomAuthenticatorFactory.java
      resources/
        META-INF/services/
          org.keycloak.authentication.AuthenticatorFactory
  target/                # Maven build output (excluded from VCS)
```

## Build

Requires Java 17 and Maven.

```
mvn clean package
```

This produces `target/custom-authenticator.jar`.

## Run Keycloak with the provider (Docker Compose)

`docker-compose.yml` mounts the built JAR into `/opt/keycloak/providers/` and starts Keycloak 24 in dev mode.

```
docker compose up
```

Keycloak Admin Console: http://localhost:8080/ (or your VM IP, e.g., http://10.72.220.223:8080/)

Credentials (dev only): `admin` / `admin`

## Configure the custom authenticator in Keycloak

1. Log in to the Admin Console.
2. Go to your realm → Authentication → Flows.
3. Create a new flow (e.g., "Custom Browser").
4. Add execution → select "Custom Authenticator (Example)" → set it to REQUIRED.
5. Bind the flow as the realm's Browser flow (Realm Settings → Authentication → Browser Flow).

## Test

1. Open a client login page (or the account console) for the realm.
2. Perform a login.
3. Check the container logs; you should see:

```
Custom Authenticator executed
```

This confirms the SPI executed and allowed the flow to proceed.

## Notes

- The provider is registered via `META-INF/services/org.keycloak.authentication.AuthenticatorFactory`.
- Built against Keycloak 24.x (Quarkus distribution), Java 17.
