package com.bansikah.keycloack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import javax.swing.text.html.HTML;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter
            jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
   @Value("${jwt.auth.converter.principal-attribute}")
    private String principalAttribute ;

    @Value("${jwt.auth.converter.resource-id}")
    private  String resourceId;
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<? extends GrantedAuthority> authorities =
                jwtGrantedAuthoritiesConverter.convert(jwt);
                extractResourceRoles(jwt).stream()
                        .collect(Collectors.toSet()
    );
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if(principalAttribute != null) {
            claimName = principalAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<?extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (jwt.getClaim(resourceId) == null){
            return Set.of();
        }
        resourceAccess = jwt.getClaim(resourceId);
        if (resourceAccess.get("bansikah") == null){
            return Set.of();
        };
        resource = (Map<String, Object>) resourceAccess.get("bansikah");

        resourceRoles = (Collection<String>) resource.get("roles");
        return resourceRoles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

}
