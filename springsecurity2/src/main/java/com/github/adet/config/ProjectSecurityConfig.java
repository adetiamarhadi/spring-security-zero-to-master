package com.github.adet.config;

import com.github.adet.filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        csrfTokenRequestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        /**
         * old
         */
//        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .cors()
//                .configurationSource((servletRequest) -> {
//                    CorsConfiguration corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//                    corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
//                    corsConfiguration.setAllowCredentials(true);
//                    corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
//                    corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
//                    corsConfiguration.setMaxAge(3600L);
//                    return corsConfiguration;
//                })
//                .and()
//                .csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//                        .ignoringRequestMatchers("/contact", "/register")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .authorizeHttpRequests()
////                .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
////                .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
////                .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
////                .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
//                .requestMatchers("/myAccount").hasRole("USER")
//                .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/myLoans").hasRole("USER")
//                .requestMatchers("/myCards").hasRole("USER")
//                .requestMatchers("/user").authenticated()
//                .requestMatchers("/notices", "/contact", "/register").permitAll()
//                .and()
//                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);

        /**
         * new
         */
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers("/contact", "/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
//                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/myLoans").authenticated()//.hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/notices", "/contact", "/register").permitAll()
                )
                .oauth2ResourceServer(
                        oauth2ResourceServer -> oauth2ResourceServer.jwt(
                                jwtCustomizer -> jwtCustomizer.jwtAuthenticationConverter(jwtAuthenticationConverter)
                        )
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
