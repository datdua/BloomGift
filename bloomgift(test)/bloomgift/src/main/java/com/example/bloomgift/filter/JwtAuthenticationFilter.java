package com.example.bloomgift.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.bloomgift.service.AccountServiece;
import com.example.bloomgift.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
// public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AccountServiece accountServiece;

    @Autowired
    private JwtUtil jwtUtil ; 

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(
                @NonNull HttpServletRequest request, 
                @NonNull HttpServletResponse response, 
                @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", authHeader);
        String username = null;
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
             token = authHeader.substring(7);
            try {
             username = jwtUtil.extractUsername(token);
                }catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                }catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired");
                }
               
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.accountServiece.loadUserByEmail(username);

                if (jwtUtil.isTokenvalid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.info("Authentication set for user: {}", username);
                }else {
                    logger.warn("Invalid token for user: {}", username);
                }
            }else{
                logger.warn("Username or Authentication is null");
            }
        }else {
            logger.warn("Authorization header is missing or malformed");
        }

        filterChain.doFilter(request, response);
    }
}


