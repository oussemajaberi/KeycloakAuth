package com.example.keycloakauth.repository;


import com.example.keycloakauth.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepository extends JpaRepository<Employe,Integer> {
}
