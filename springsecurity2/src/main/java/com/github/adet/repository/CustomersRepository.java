package com.github.adet.repository;

import com.github.adet.model.Customers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customers, Long> {

    List<Customers> findByEmail(String email);
}
