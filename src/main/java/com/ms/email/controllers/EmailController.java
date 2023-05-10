package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {

    Logger logger = LogManager.getLogger(EmailController.class);

    @Autowired
    EmailService emailService;


    @PostMapping("/emails")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {                          //recebe o email DTO e valida
        return new ResponseEntity<>(emailService.sendEmail(emailDto.convertToEmailModel()), HttpStatus.CREATED);     // faz conversao de DTO para MODEL
    }                                                                                                                // retorna para o cliente o emailMODEL e o status create


    //lista todos os emails
    @GetMapping("/emails")
    public ResponseEntity<Page<EmailModel>> getAllEmails(@PageableDefault(page = 0, size = 5, sort = "emailId", direction = Sort.Direction.DESC) Pageable pageable){
        logger.trace("TRACE");         //log para obter detalhamento/minusioso
        logger.debug("DEBUG");         //log para inf para desenvolvedores
        logger.info("INFO");           //log não tras tantos detalhes / determinada logs em produções
        logger.warn("WARN");           //log de alertas/conflitos/perda de dados
        logger.error("ERROR");         //log de error / inesperado
        logger.fatal("FATAL");         //log quando ocorre um erro critico / um funcionalidade para / afeta o sistema como todo
        return new ResponseEntity<>(emailService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/emails/{emailId}")
    public ResponseEntity<Object> getOneEmail(@PathVariable(value="emailId") UUID emailId){
        Optional<EmailModel> emailModelOptional = emailService.findById(emailId);
        if(!emailModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(emailModelOptional.get());
        }
    }


}
























