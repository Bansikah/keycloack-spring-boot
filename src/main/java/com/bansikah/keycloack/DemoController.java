package com.bansikah.keycloack;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @RequestMapping("/hello")
    @PreAuthorize("hasRole('client_user')")
    public String hello() {
        return "Hello World! from keycloak and spring app";
    }

    @RequestMapping("/hello2")
    @PreAuthorize("hasRole('client_admin')")
    public String hello2() {
        return "Hello World 2! from keycloak and spring app - ADMIN";
    }
}
