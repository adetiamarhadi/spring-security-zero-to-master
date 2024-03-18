package com.github.adet.controller;

import com.github.adet.model.Accounts;
import com.github.adet.model.Customers;
import com.github.adet.repository.AccountsRepository;
import com.github.adet.repository.CustomersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    private final AccountsRepository accountsRepository;
    private final CustomersRepository customersRepository;

    public AccountController(AccountsRepository accountsRepository,
                             CustomersRepository customersRepository) {
        this.accountsRepository = accountsRepository;
        this.customersRepository = customersRepository;
    }

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {

        List<Customers> customers = customersRepository.findByEmail(email);
        if (null == customers || customers.isEmpty()) {
            return null;
        }

        return accountsRepository.findByCustomerId(customers.get(0).getId());
    }

}
