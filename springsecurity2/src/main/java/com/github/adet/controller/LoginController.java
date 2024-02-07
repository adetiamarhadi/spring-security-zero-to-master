package com.github.adet.controller;

import com.github.adet.model.Customers;
import com.github.adet.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
public class LoginController {

    private final CustomersRepository customersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(CustomersRepository customersRepository,
                           PasswordEncoder passwordEncoder) {
        this.customersRepository = customersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customers customers) {
        try {

            String encodedPassword = this.passwordEncoder.encode(customers.getPwd());

            customers.setPwd(encodedPassword);
            customers.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));

            this.customersRepository.save(customers);

            return ResponseEntity.status(HttpStatus.CREATED).body("successfully created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("registerUser: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public Customers getUserDetailsAfterLogin(Authentication authentication) {
        List<Customers> customers = customersRepository.findByEmail(authentication.getName());
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            return null;
        }
    }
}
