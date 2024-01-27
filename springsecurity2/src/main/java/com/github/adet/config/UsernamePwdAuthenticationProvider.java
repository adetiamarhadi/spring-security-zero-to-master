package com.github.adet.config;

import com.github.adet.model.Customers;
import com.github.adet.repository.CustomersRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final CustomersRepository customersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsernamePwdAuthenticationProvider(CustomersRepository customersRepository,
                                             PasswordEncoder passwordEncoder) {
        this.customersRepository = customersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final String username = authentication.getName();
        final String pwd = authentication.getCredentials().toString();

        List<Customers> customers = this.customersRepository.findByEmail(username);
        if (customers.isEmpty()) {
            throw new BadCredentialsException("no user registered with this details");
        }

        Customers customer = customers.get(0);

        if (!this.passwordEncoder.matches(pwd, customer.getPwd())) {
            throw new BadCredentialsException("invalid password");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(customer.getRole());

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(grantedAuthority);

        return new UsernamePasswordAuthenticationToken(username, pwd, grantedAuthorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
