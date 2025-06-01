package com.nghex.exe202.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) {
        try {
            // Validate email format
            if (!isValidEmail(userEmail)) {
                logger.error("Invalid email address: {}", userEmail);
                throw new IllegalArgumentException("Invalid email format: " + userEmail);
            }

            // Tạo nội dung HTML email
            String htmlContent = """
                        <div style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 30px;">
                            <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 8px; 
                                        box-shadow: 0 0 10px rgba(0,0,0,0.1); padding: 20px;">
                                <h2 style="color: #333333; text-align: center;">🔐 Xác minh tài khoản</h2>
                                <p>Xin chào <strong>%s</strong>,</p>
                                <p>Bạn vừa yêu cầu mã xác minh OTP. Đây là mã của bạn:</p>
                                <div style="font-size: 26px; font-weight: bold; background-color: #f0f0f0; 
                                            padding: 12px; text-align: center; border-radius: 6px; margin: 20px 0;">
                                    %s
                                </div>
                                <p>Mã OTP này sẽ hết hạn sau 5 phút. Vui lòng không chia sẻ mã này với bất kỳ ai.</p>
                                <p style="margin-top: 30px;">Trân trọng,<br/>Hệ thống hỗ trợ</p>
                                <p>%s</p>
                            </div>
                        </div>
                    """.formatted(userEmail, otp, text);

            // Tạo email
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = gửi dạng HTML
            helper.setTo(userEmail);

            // Gửi email
            javaMailSender.send(mimeMessage);

            logger.info("Email sent successfully to {}", userEmail);

        } catch (MailException e) {
            logger.error("Failed to send email to {}: {}", userEmail, e.getMessage());
            throw new MailSendException("Failed to send email: " + e.getMessage(), e);
        } catch (MessagingException e) {
            logger.error("MessagingException while sending email to {}: {}", userEmail, e.getMessage());
            throw new RuntimeException("Error while constructing email: " + e.getMessage(), e);
        }

    }
      /*
    public void sendVerificationOtpEmail(String userEmail, String otp, String subject, String text) throws MessagingException, MailSendException {


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


            helper.setSubject(subject);
            helper.setText(text+otp, true);
            helper.setTo(userEmail);
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            throw new MailSendException("Failed to send email");
        }
    } */

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

}
