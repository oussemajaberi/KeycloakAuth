package com.example.keycloakauth.requests;

import lombok.Getter;

@Getter
public class LoginRequest {
    String username;
    String password;
}
