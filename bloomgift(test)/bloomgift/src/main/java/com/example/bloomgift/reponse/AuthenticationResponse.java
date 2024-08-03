package com.example.bloomgift.reponse;

import jakarta.annotation.sql.DataSourceDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// public class AuthenticationResponse {
//     private final String token;

//     public AuthenticationResponse(String token) {
//         this.token = token;
//     }

//     public String getToken() {
//         return token;
//     }

    // public class AuthenticationResponse {
    
    //     private String token;
    // }
    

    public class AuthenticationResponse {
        private String token;
    
        // Constructors, getters, setters...
    
        public static Builder builder() {
            return new Builder();
        }
    
        public static class Builder {
            private String token;
    
            public Builder token(String token) {
                this.token = token;
                return this;
            }
    
            public AuthenticationResponse build() {
                AuthenticationResponse response = new AuthenticationResponse();
                response.setToken(this.token);
                return response;
            }
        }
    }

