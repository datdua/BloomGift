package com.example.bloomgift.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Xác Nhận OTP");
    
        // Define the email content with a hyperlink using string concatenation
        mimeMessageHelper.setText(""
            + "Your OTP code is:"+ otp + ""
            + "Thank you!</"
            + "".format(email,otp));
    
        // Set the email content as HTML
        
        // Send the email
        javaMailSender.send(mimeMessage);
    }

    
    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Set password");
    
        // Define the email content with a hyperlink using string concatenation
        String emailContent = "<p>Click the link below to set your password:</p>"
        + "<p><a href= Set Password</a></p>"
        + "<p>Thank you!</p>";

// Set the email content as HTML
mimeMessageHelper.setText(emailContent, true);

        javaMailSender.send(mimeMessage);
    }

}
