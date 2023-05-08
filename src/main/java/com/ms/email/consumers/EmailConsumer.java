package com.ms.email.consumers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;                              // ponto de injeção do email service

    @RabbitListener(queues = "${spring.rabbitmq.queue}")                // metodo que vai escutar a fila, a fila que ja definimos queue
    public void listen(@Payload EmailDto emailDto){                     // recebe payload de emailDto
        EmailModel emailModel = new EmailModel();                       // cria uma instacia de emailModel
        BeanUtils.copyProperties(emailDto, emailModel);                 // transforma emailDto em emailModel
        emailService.sendEmail(emailModel);
        System.out.println("Email Status: " + emailModel.getStatusEmail().toString());
    }


}
