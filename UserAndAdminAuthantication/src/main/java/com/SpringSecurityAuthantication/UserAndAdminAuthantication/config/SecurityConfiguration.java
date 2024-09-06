package com.SpringSecurityAuthantication.UserAndAdminAuthantication.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.SpringSecurityAuthantication.UserAndAdminAuthantication.jwt.JWTAuthanticationFilter;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.pojo.User;
import com.SpringSecurityAuthantication.UserAndAdminAuthantication.repository.UserRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IlleagalAccess illeagalAccess;

    @Autowired
    private JWTAuthanticationFilter jwtAuthanticationFilter;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            User users = userRepository.getUserByUserName(username);
            if (users == null) {
                throw new UsernameNotFoundException("User not found with this username: " + username);
            }
            return org.springframework.security.core.userdetails.User.withUsername(users.getUserName())
                    .password(users.getPassword()).roles(users.getRole()).build();
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/api/registerUser", "/dashboard").permitAll()
                .requestMatchers("/api/products/allProducts").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/products/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .exceptionHandling(exception -> exception.accessDeniedHandler(illeagalAccess))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthanticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/api/auth/login?error");
        return failureHandler;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
