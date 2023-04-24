package com.example.keycloakauth.controller;

import com.example.keycloakauth.configuration.KeycloakAdminClientService;
import com.example.keycloakauth.configuration.KeycloakProvider;
import com.example.keycloakauth.model.Employe;
import com.example.keycloakauth.repository.EmployeRepository;
import com.example.keycloakauth.requests.CreateUserRequest;
import com.example.keycloakauth.requests.LoginRequest;
import com.sun.istack.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/user")
public class EmployeeController {

    @Autowired
    private EmployeRepository employeRepository;
    private final KeycloakAdminClientService kcAdminClient;
    private final KeycloakProvider kcProvider;
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(KeycloakAdminClientService kcAdminClient, KeycloakProvider kcProvider) {
        this.kcProvider = kcProvider;
        this.kcAdminClient = kcAdminClient;
    }




    @PostMapping(value = "/create")
    public ResponseEntity<Response> createUser(@RequestBody CreateUserRequest user) {
        Response createdResponse = kcAdminClient.createKeycloakUser(user);
        return ResponseEntity.ok(createdResponse);

    }

    @PostMapping("/login")

    public ResponseEntity<AccessTokenResponse> login(@NotNull @RequestBody LoginRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (BadRequestException ex) {
              LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }

    }

    @PostMapping("/employees")
    @RolesAllowed("collaborateur")
    public ResponseEntity<Employe> save(@RequestBody Employe employe) {
        return ResponseEntity.ok(employeRepository.save(employe));
    }

    @GetMapping("/employees")
    @RolesAllowed("chef_de_projet")
    public ResponseEntity<List<Employe>> findAll() {
        return ResponseEntity.ok(employeRepository.findAll());
    }

}
