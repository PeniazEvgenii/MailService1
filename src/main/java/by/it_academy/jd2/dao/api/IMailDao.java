package by.it_academy.jd2.dao.api;

import by.it_academy.jd2.entity.EStatus;
import by.it_academy.jd2.entity.MailEntity;

import java.util.List;

public interface IMailDao {

    MailEntity save(MailEntity mailEntity);

    List<MailEntity> getAll();

    MailEntity updateStatus(MailEntity mailEntity, EStatus status);
}
