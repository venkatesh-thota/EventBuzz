package com.stackroute.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    private String from;
    private String[] bcc;
    private String subject;
    private String body;
    private String sendAt;
    private String name;
    private String eventUrl;
}
