package backend.service.utils;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmailService {

    private JavaMailSender mailSender;

    public void sendEmail(String to, String texte, String subject) throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(texte);
        mailSender.send(message);

    }

}