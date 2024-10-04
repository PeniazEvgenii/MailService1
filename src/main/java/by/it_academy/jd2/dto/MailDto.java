package by.it_academy.jd2.dto;

import by.it_academy.jd2.entity.EStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class MailDto {
    private String title;
    private String body;
    private String recipient;

    public MailDto(String title, String body, String recipient) {
        this.title = title;
        this.body = body;
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getRecipient() {
        return recipient;
    }
}
