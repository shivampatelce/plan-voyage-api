package com.example.plan_voyage.services.impl;

import com.example.plan_voyage.services.KeycloakService;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakServiceImpl implements KeycloakService {
    @Value("${keycloak.config.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.config.realm}")
    private String realm;

    @Value("${keycloak.config.admin-client-id}")
    private String adminClientId;

    @Value("${keycloak.config.admin-client-secret}")
    private String adminClientSecret;

    private Keycloak getAdminKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        Keycloak keycloak = getAdminKeycloakInstance();
        UsersResource usersResource = keycloak.realm(realm).users();
        return usersResource.get(userId).toRepresentation();
    }

    @Override
    public boolean isUserExistsByEmail(String email) {
        Keycloak keycloak = getAdminKeycloakInstance();
        UsersResource usersResource = keycloak.realm(realm).users();

        return !usersResource.search(email, true).isEmpty();
    }


}
