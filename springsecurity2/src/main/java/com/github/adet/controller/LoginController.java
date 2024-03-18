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

    @Autowired
    public LoginController(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @GetMapping("/user")
    public Customers getUserDetailsAfterLogin(Authentication authentication) {
        List<Customers> customers = customersRepository.findByEmail(authentication.getName());
        if (null == customers || customers.isEmpty()) {
            return null;
        }
        return customers.get(0);
    }
}
