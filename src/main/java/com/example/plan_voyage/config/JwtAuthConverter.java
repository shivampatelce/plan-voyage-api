package com.example.plan_voyage.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    @Value("${keycloak.config.resource-access}")
    private String resourceAccessKey;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Set<GrantedAuthority> authorities = Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRole(jwt).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(jwt, authorities, getPrincipleClaimName(jwt));
    }

    private String getPrincipleClaimName(Jwt jwt) {
        return jwt.getClaim("preferred_username");
    }

    private Collection<? extends GrantedAuthority> extractResourceRole(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Collection<String> resourceRoles;

        if(jwt.getClaim(resourceAccessKey) == null) {
            return Set.of();
        }

        resourceAccess = jwt.getClaim(resourceAccessKey);

        if(resourceAccess.get("roles") == null) {
            return Set.of();
        }

        resourceRoles = (Collection<String>) resourceAccess.get("roles");

        return resourceRoles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toSet());
    }
}
