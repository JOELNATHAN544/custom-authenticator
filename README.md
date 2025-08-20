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

graph TD
    subgraph "Keycloak Core"
        KC[Keycloak IAM Server] -->|Uses extensibility points via| SPI[SPI Interfaces\n\(e.g., Authenticator, UserStorageProvider\)]
        SPI -->|Loaded dynamically at runtime using| SL[Java ServiceLoader]
    end

    subgraph "Custom Extension Development"
        DEV[Developer] -->|1. Identifies SPI to extend\n\(e.g., Authentication SPI\)| IMP[2. Implements Provider\nand ProviderFactory interfaces]
        IMP -->|3. Creates META-INF/services/\nconfiguration file| BUILD[4. Builds JAR with dependencies\n\(e.g., keycloak-services\)]
    end

    subgraph "Deployment and Integration"
        BUILD -->|5. Deploys JAR to\nKeycloak's /providers/ directory| DEP[6. Keycloak rebuilds and starts\n\(e.g., bin/kc.sh build\)]
        DEP -->|7. Configures provider\n\(CLI, Admin Console, or realm settings\)| RUN[8. Runtime Usage:\nKeycloak calls custom provider\nvia KeycloakSession]
    end

    KC -->|Relies on for modularity| SPI
    SL -->|Discovers and registers| IMP
    RUN -->|Enables custom functionality\n\(e.g., custom auth, user federation\)| KC

    style KC fill:#f9f,stroke:#333
    style SPI fill:#bbf,stroke:#333
