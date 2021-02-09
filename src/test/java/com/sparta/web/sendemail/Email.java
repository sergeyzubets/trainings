package com.sparta.web.sendemail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Email {

    String recipient;
    @JsonIgnore
    String subject;
    @JsonIgnore
    String body;

}
