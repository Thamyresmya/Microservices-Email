package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    //metodo post para uri definida o sendingEmail vai responder recebendo como paramentro EmailDto
    @PostMapping("/email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {     //recebe o email DTO e valida
        EmailModel emailModel = new EmailModel();                                               // transforma o DTO em model para salvar no BD
        BeanUtils.copyProperties(emailDto, emailModel);                                         // metodo copyProperties -> faz conversao de DTO para MODEL
        emailService.sendEmail(emailModel);                                                     // metodo sendEmail dentro do emailSERVICE -> salva e enviar o email
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);                            // retorna para o cliente o emailMODEL e o status create
    }


}
























