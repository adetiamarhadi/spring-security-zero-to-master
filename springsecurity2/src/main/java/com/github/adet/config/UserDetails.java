package com.github.adet.config;

import com.github.adet.model.Customers;
import com.github.adet.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetails implements UserDetailsService {

    final CustomersRepository customersRepository;

    @Autowired
    public UserDetails(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String email = null;
        String password = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Customers> customers = this.customersRepository.findByEmail(username);
        if (null == customers || customers.isEmpty()) {
            throw new UsernameNotFoundException("User details not found for user " + username);
        } else {
            email = customers.get(0).getEmail();
            password = customers.get(0).getPwd();
            authorities.add(new SimpleGrantedAuthority(customers.get(0).getRole()));
        }
        return new User(email, password, authorities);
    }
}
