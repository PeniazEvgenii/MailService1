package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.MailDto;
import by.it_academy.jd2.entity.MailEntity;

public interface IMailService {

    boolean send(MailEntity mail);
}
