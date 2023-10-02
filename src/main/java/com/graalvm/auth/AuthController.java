package com.graalvm.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(OK)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    String login(@Valid @RequestBody AuthDTO dto) {
        return this.authService.login(dto);
    }

    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_read')")
    String privateRoute() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
