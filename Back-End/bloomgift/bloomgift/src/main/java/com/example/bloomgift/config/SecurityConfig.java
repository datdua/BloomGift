package com.example.bloomgift.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.bloomgift.filter.JwtAuthenticationFilter;
import com.example.bloomgift.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        private static final String[] SWAGGER_URL = {
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/api-docs/**",
                        "/api/auth/**",
                        "/api/product/**",

        };

        private static final String[] GUEST_URL = { "/api/guest/**", "/api/auth/**", "/api/accounts/**", "/api/combos/**",
                        "/api/customer/category/**", "/api/customer/product/**", "/api/customer/promotion/**", "/api/customer/store/**" };

        private static final String[] ADMIN_URL = { "/api/admin/**", "/api/google-sheets/**" };

        private static final String[] CUSTOMER_URL = { "/api/customer/**", "/api/promotion/**", "/api/store/**" };

        private static final String[] MANAGER_URL = { "/api/manager/**" };

        private static final String[] SELLER_URL = { "/api/seller/**" };

        private static final String[] ADMIN_SELLER_URL = { "/api/store/**", "/api/product/**", "/api/promotion/**",
                        "/api/manager/**", "/api/seller/**", "/api/customer/**", "/api/admin/**", "/api/revenue/**",
                        "/api/chats/**" };

        private static final String[] ADMIN_MANAGER_SALE_STAFF_URL = {};

        @Autowired
        private final AccountService UserService;

        @Autowired
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(AccountService UserService, JwtAuthenticationFilter jwtAuthenticationFilter) {
                this.UserService = UserService;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(SWAGGER_URL).permitAll()
                        .requestMatchers(CUSTOMER_URL).hasRole("CUSTOMER")
                        .requestMatchers(ADMIN_URL).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("http://localhost:8080/oauth2/authorization/google")
                        .defaultSuccessUrl("http://localhost:8080/api/auth/signInWithGoogle", true))
                .formLogin(Customizer.withDefaults());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(
                                Arrays.asList("http://localhost:3000", "https://the-diamond-store-demo.web.app",
                                                "https://www.thediamondstore.site"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("*"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}