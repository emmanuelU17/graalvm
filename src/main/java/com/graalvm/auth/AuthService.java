package com.graalvm.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserDetailsManager detailsManager;
    private final JwtEncoder encoder;

    public String login(AuthDTO dto) {

        boolean exists = this.detailsManager.userExists(dto.principal());

        if (!exists) {
            throw new RuntimeException("User does not exist");
        }

        var details = this.detailsManager.loadUserByUsername(dto.principal());

        var authenticated = UsernamePasswordAuthenticationToken
                .authenticated(details, null, details.getAuthorities());

        log.info(dto.principal() + " logged in");

        return jwt(authenticated);
    }

    private String jwt(Authentication authentication) {
        String scope = authentication
                .getAuthorities() //
                .stream() //
                .map(GrantedAuthority::getAuthority) //
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
