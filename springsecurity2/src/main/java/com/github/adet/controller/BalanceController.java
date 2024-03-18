package com.github.adet.controller;

import com.github.adet.model.AccountTransactions;
import com.github.adet.model.Customers;
import com.github.adet.repository.AccountTransactionsRepository;
import com.github.adet.repository.CustomersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final CustomersRepository customersRepository;

    public BalanceController(AccountTransactionsRepository accountTransactionsRepository,
                             CustomersRepository customersRepository) {
        this.accountTransactionsRepository = accountTransactionsRepository;
        this.customersRepository = customersRepository;
    }

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {

        List<Customers> customers = customersRepository.findByEmail(email);
        if (null == customers || customers.isEmpty()) {
            return null;
        }

        return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(customers.get(0).getId());
    }
}
