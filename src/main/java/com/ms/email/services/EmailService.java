package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import com.ms.email.repositories.EmailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    Logger logger = LogManager.getLogger(EmailService.class);

    @Autowired
    EmailRepository emailRepository;
    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        try{
            emailModel.setSendDateEmail(LocalDateTime.now());                       //data e hora de envio

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());                    // quem esta enviando o email
            message.setTo(emailModel.getEmailTo());                        // quem vai receber o email
            message.setSubject(emailModel.getSubject());                   // titulo do email
            message.setText(emailModel.getText());                         // corpo do email
            emailSender.send(message);                                     //envia o email

            emailModel.setStatusEmail(StatusEmail.SENT);                   //status sent caso tudo ok
            logger.info("Email sent successfully to: {} ", emailModel.getEmailTo());
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);                  //caso tenha um erro
            logger.error("Email with error: {} ", emailModel.toString());
            logger.error("Error {} ", e);
        } finally {
            emailModel = emailRepository.save(emailModel);                //salva no BD
            logger.info("Email saved successfully emailId: {} ", emailModel.getEmailId());
            return emailModel;
        }
    }


    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }


}
