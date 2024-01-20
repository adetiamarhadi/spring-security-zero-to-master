package com.github.adet.controller;

import com.github.adet.model.Customers;
import com.github.adet.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final CustomersRepository customersRepository;

    @Autowired
    public LoginController(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customers customers) {
        try {
            this.customersRepository.save(customers);
            return ResponseEntity.status(HttpStatus.CREATED).body("successfully created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("registerUser: " + e.getMessage());
        }
    }
}
