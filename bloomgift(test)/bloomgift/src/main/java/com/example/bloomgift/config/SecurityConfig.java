package com.example.bloomgift.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bloomgift.filter.JwtAuthenticationFilter;
import com.example.bloomgift.service.AccountServiece;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private final AccountServiece accountServiece;
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter; 

    @Autowired
    public SecurityConfig(AccountServiece accountServiece, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.accountServiece = accountServiece;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    
    // @Bean 
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    //     return http
    //                 .csrf(AbstractHttpConfigurer :: disable)
    //                 .authorizeHttpRequests( 
    //                 req -> req.requestMatchers("/api/auth/**") AuthorizedUrl
    //                          .permitAll() AuthorizationManagerRequestMatcherRegistry
    //                          .anyRequest() AuthorizedUrl
    //                          .authenticated()
    //                 ).userDetailsService(accountServiece)
    //                 .sessionManagerment(session -> session
    //                         .sessionCreationPolicy(sessionCreationPolicy.STATELESS))
    //                 .addFillterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    //                 .build();
                     
    // }
    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(req -> req
    //             .requestMatchers("/api/auth/**").permitAll() // Cho phép truy cập vào /api/auth/** mà không cần xác thực
    //             .anyRequest().authenticated()
    //              // Yêu cầu xác thực cho tất cả các yêu cầu khác
    //         )
    //         .sessionManagement(session -> session
    //             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //         )
    //         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    //     return http.build();
    // }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    // @Autowired
    // public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(accountServiece).passwordEncoder(passwordEncoder());
    // }
}
