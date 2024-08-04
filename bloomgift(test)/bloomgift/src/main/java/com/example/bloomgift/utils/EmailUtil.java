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
        mimeMessageHelper.setText("<html>"
            + "<body>"
            + "<p>Dear User,</p>"
            + "<p>Your OTP code is: <strong>" + otp + "</strong></p>"
            + "<p>Please verify your email by clicking the following link:</p>"
            + "<p><a href=\"http://localhost:8080/verify?email=" + email + "\">Verify Email</a></p>"
            + "<p>If you did not request this, please ignore this email.</p>"
            + "<p>Thank you!</p>"
            + "<p>Best regards,<br>Your Company</p>"
            + "</body>"
            + "</html>".format(email,otp));
    
        // Set the email content as HTML
        
        // Send the email
        javaMailSender.send(mimeMessage);
    }

    public String loadEmailTemplate_OtpEmail() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("template/OtpEmail.html");

        if (inputStream == null) {
            throw new IOException("File not found: template/OtpEmail.html");
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }

        return content.toString();
    }
    public void sendSetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("set password");
        
        String emailContent;
        try {
            emailContent = loadEmailTemplate_OtpEmail();
            
            emailContent = emailContent.replace("{{email}}", email);
        } catch (IOException e) {
            emailContent = "ko doc duoc ";
        }
        
        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

}
