package com.ms.email.dtos;

import com.ms.email.models.EmailModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class EmailDto {

    @NotBlank
    private String ownerRef;
    @NotBlank
    @Email
    private String emailFrom;
    @NotBlank
    @Email
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;

    public EmailModel convertToEmailModel(){
        var emailModel = new EmailModel();                           // cria uma instacia de emailModel
        BeanUtils.copyProperties(this, emailModel);           // transforma emailDto em emailModel
        return emailModel;
    }



}
