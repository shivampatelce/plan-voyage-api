package com.example.plan_voyage.services;

import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {
    UserRepresentation getUserById(String userId);
}
