package com.example.plan_voyage.services;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    UserRepresentation getUserById(String userId);

    boolean isUserExistsByEmail(String email);

    UserRepresentation getUserByEmail(String email);
}
