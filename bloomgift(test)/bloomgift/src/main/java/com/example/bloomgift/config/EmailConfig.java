package com.example.bloomgift.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
     @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl JavaMailSender = new JavaMailSenderImpl();
        JavaMailSender.setHost(mailHost);
        JavaMailSender.setPort(mailPort);
        JavaMailSender.setUsername(mailUsername);
        JavaMailSender.setPassword(mailPassword);

        Properties props = JavaMailSender.getJavaMailProperties();
        props.put("mail.smtp.starttls.enable", "true");
        return JavaMailSender;
    }
}
