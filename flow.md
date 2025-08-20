# Service Provider Interface (SPI)

## Introduction to SPI in Keycloak

### What is SPI

The Service Provider Interface (SPI) is a framework in Keycloak that enables extensibility. It allows you to plug in custom implementations 

(called providers) for various components, addressing use cases not covered by built-in features. For instance, you can create custom 

authentication mechanisms, theme selectors, or integrate with external user databases.

They are used to extend the capablity of keycloak, access management system programatically and build new functionalities. SPIs (Service Provider 

Interfaces) are extension points that let developers replace or augment Keycloak’s built-in components—terms like authentication, user storage, 

event listening, theming, and token management are all handled via SPIs.



### Why Use SPI

- **Flexibility**: Extend Keycloak for specific needs, like connecting to legacy systems or adding new protocols.

- **Modularity**: Avoid forking the codebase; deploy extensions as separate JARs.

- **Types of Providers**:

    - **Single-Implementation**: Only one active provider (e.g., hostname configuration).

    - **Multiple-Implementation**: Several can coexist (e.g., multiple event listeners for different logging outputs).

### Keycloak Extension Points

SPI covers areas like:

- Authentication flows and authenticators.
- User federation and storage.
- Event handling.
- Themes and templates.
- Vault for secret management.

## Core Concepts

### Provider and ProviderFactory Interfaces

To implement an SPI:

- **Provider**: The actual implementation that performs the work (e.g., authenticating a user).

- **ProviderFactory**: Creates instances of the Provider and handles initialization, configuration, and lifecycle.

Every SPI requires implementing both. Providers can access other components via the KeycloakSession object.

### Service Configuration File

Providers are discovered via Java's ServiceLoader mechanism. Create a file in META-INF/services/ named after the factory interface (e.g., META-INF/services/org.keycloak.theme.ThemeSelectorProviderFactory), containing the fully qualified name of your factory class.

### Lifecycle Methods

- init(Config.Scope config): For configuration loading.

- postInit(KeycloakSessionFactory factory): Post-initialization hooks.

- create(KeycloakSession session): Instantiates the provider.

- close(): Cleanup.

### FEW AVAILABLE SPIs

We have:

- **Authentication SPI**: Have plugins like password, OTP, and u can

create your own one.

- **User storage SPI**: Use it to write extensions to connect to exter-

nal user databases and credentials stores.

- **SAML Role Mappings SPIs**: Mapping SAML roles into roles that exist

in the service provider
