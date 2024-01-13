package com.github.adet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        /**
         * old
         */
//        http.authorizeHttpRequests()
//                .requestMatchers("/myAccounts", "/myBalance", "/myLoans", "/myCards").authenticated()
//                .requestMatchers("/notices", "/contact").permitAll()
//                .and().formLogin()
//                .and().httpBasic();

        /**
         * new
         */
        http.authorizeHttpRequests(request -> request
                        .requestMatchers("/myAccounts", "/myBalance", "/myLoans", "/myCards").authenticated()
                        .requestMatchers("/notices", "/contact").permitAll()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
