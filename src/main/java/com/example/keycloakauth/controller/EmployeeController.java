package com.example.keycloakauth.controller;

import com.example.keycloakauth.model.Employe;
import com.example.keycloakauth.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeRepository employeRepository;

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
