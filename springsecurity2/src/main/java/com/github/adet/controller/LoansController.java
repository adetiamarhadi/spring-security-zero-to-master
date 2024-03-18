package com.github.adet.controller;

import com.github.adet.model.Customers;
import com.github.adet.model.Loans;
import com.github.adet.repository.CustomersRepository;
import com.github.adet.repository.LoanRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    private final LoanRepository loanRepository;
    private final CustomersRepository customersRepository;

    public LoansController(LoanRepository loanRepository,
                           CustomersRepository customersRepository) {
        this.loanRepository = loanRepository;
        this.customersRepository = customersRepository;
    }

    @GetMapping("/myLoans")
    @PostAuthorize("hasRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam String email) {

        List<Customers> customers = customersRepository.findByEmail(email);
        if (null == customers || customers.isEmpty()) {
            return null;
        }

        return loanRepository.findByCustomerIdOrderByStartDtDesc(customers.get(0).getId());
    }

}
