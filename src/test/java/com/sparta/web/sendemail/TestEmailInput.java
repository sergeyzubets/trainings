package com.sparta.web.sendemail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.web.WebUtils;
import lombok.Data;

@Data
public class TestEmailInput {

    String username;
    String password;
    String emailRecipient;
    @JsonIgnore
    String emailSubject;
    String emailBody;

    public TestEmailInput() {
        setEmailSubject(WebUtils.getDate());
        setEmailBody(WebUtils.getMultiLineEmailBodyWithDate());
    }
}
