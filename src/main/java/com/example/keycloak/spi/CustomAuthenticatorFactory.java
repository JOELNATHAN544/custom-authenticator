package com.example.keycloak.spi;

import java.util.Collections;
import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

public class CustomAuthenticatorFactory implements AuthenticatorFactory {

    public static final String PROVIDER_ID = "custom-authenticator";
    private static final CustomAuthenticator SINGLETON = new CustomAuthenticator();

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Custom Authenticator (Example)";
    }

    @Override
    public String getHelpText() {
        return "Logs a message and continues the flow.";
    }

    @Override
    public String getReferenceCategory() {
        return null; // no reference category
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[] { Requirement.REQUIRED, Requirement.ALTERNATIVE, Requirement.DISABLED };
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;
    }

    @Override
    public void init(Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }
}


