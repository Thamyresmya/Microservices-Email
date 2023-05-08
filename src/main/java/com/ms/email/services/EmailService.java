package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());                  //data e hora de envio
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());                    // quem esta enviando o email
            message.setTo(emailModel.getEmailTo());                        // quem vai receber o email
            message.setSubject(emailModel.getSubject());                   // titulo do email
            message.setText(emailModel.getText());                         // corpo do email
            emailSender.send(message);                                     //envia o email

            emailModel.setStatusEmail(StatusEmail.SENT);                   //status sent caso tudo ok
        } catch (MailException e ){
            emailModel.setStatusEmail(StatusEmail.ERROR);                   //caso tenha um erro
        } finally {
            return emailRepository.save(emailModel);                         //salva no BD
        }

    }
}
