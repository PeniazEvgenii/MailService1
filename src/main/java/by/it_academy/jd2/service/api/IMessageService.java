package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dto.MailDto;
import by.it_academy.jd2.entity.MailEntity;

import java.util.List;

public interface IMessageService {
    Long create(MailDto mailDto);

    List<MailEntity> getAll();

    String getAllJson();
}
