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
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Xác Thực Mã OTP BloomGift");

        String emailContent;
        try {
            emailContent = loadEmailTemplate_OtpEmail();
            String username = email.split("@")[0];
            emailContent = emailContent.replace("{{username}}", username);
            emailContent = emailContent.replace("{{otp}}", otp);
            emailContent = emailContent.replace("{{email}}", email);
        } catch (IOException e) {
            emailContent = "Default email content if template cannot be read";
        }

        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    public String loadEmailTemplate_OtpEmail() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("template/template-mail-OTP.html");

        if (inputStream == null) {
            throw new IOException("File not found: template/template-mail-OTP.html");
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
        mimeMessageHelper.setSubject("Yêu Cầu Đặt Lại Mật Khẩu BloomGift");

        String emailContent;
        try {
            emailContent = loadEmailTemplate_SetPassword();
            String username = email.split("@")[0];
            emailContent = emailContent.replace("{{username}}", username);
            emailContent = emailContent.replace("{{email}}", email);
        } catch (IOException e) {
            emailContent = "Default email content if template cannot be read";
        }

        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }

    public String loadEmailTemplate_SetPassword() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("template/template-mail-reset-password.html");

        if (inputStream == null) {
            throw new IOException("File not found: template/template-mail-reset-password.html");
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
}
