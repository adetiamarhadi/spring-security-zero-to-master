package com.github.adet.controller;

import com.github.adet.model.Cards;
import com.github.adet.model.Customers;
import com.github.adet.repository.CardsRepository;
import com.github.adet.repository.CustomersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    private final CardsRepository cardsRepository;
    private final CustomersRepository customersRepository;

    public CardsController(CardsRepository cardsRepository,
                           CustomersRepository customersRepository) {
        this.cardsRepository = cardsRepository;
        this.customersRepository = customersRepository;
    }

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {

        List<Customers> customers = customersRepository.findByEmail(email);
        if (null == customers || customers.isEmpty()) {
            return null;
        }

        return cardsRepository.findByCustomerId(customers.get(0).getId());
    }

}
