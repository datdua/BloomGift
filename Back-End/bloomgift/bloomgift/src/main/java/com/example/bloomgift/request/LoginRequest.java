package com.example.bloomgift.request;

public class LoginRequest {
    private String email; 
    private String passrword; 

    public LoginRequest(){

    }

    public LoginRequest(String email, String passrword) {
        this.email = email;
        this.passrword = passrword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassrword() {
        return passrword;
    }

    public void setPassrword(String passrword) {
        this.passrword = passrword;
    }

    
}
