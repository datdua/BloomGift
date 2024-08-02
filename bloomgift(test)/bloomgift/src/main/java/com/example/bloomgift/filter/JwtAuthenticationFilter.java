package com.example.bloomgift.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.bloomgift.service.AccountServiece;
import com.example.bloomgift.utils.JwtUtil;

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
    public JwtAuthenticationFilter(JwtUtil jwtUtil,AccountServiece accountServiece){
        this.accountServiece = accountServiece;
        this.jwtUtil = jwtUtil;
    }
    @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // String username = null;
        // String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // jwt = authorizationHeader.substring(7);
            // username = jwtUtil.extractUsername(jwt);
            filterChain.doFilter(request, response);
            return ; 
        }
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.accountServiece.loadUserByEmail(username);
            if(jwtUtil.isvalid(token, userDetails)){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
            );
        
            usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
                        
                        );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
                       
                
            
        }
        filterChain.doFilter(request, response);
    }
}
