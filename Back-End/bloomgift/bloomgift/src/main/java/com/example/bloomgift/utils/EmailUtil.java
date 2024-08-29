package com.example.bloomgift.utils;

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


    public void sendForgetPasswordEmail(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Quên Mật Khẩu");

        // Tạo nội dung email
        String content = "<p>Bạn đã yêu cầu quên mật khẩu.</p>"
                + "<p>Vui lòng bấm vào nút dưới đây để reset mật khẩu.</p>"
                + "<a href='http://localhost:3000/reset-password?email=" + email + "'>Reset Password</a>";

        mimeMessageHelper.setText(content, true);

        // Gửi email
        javaMailSender.send(mimeMessage);
    }
}
