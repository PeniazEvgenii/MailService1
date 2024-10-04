package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IMailDao;
import by.it_academy.jd2.dao.exception.DaoException;
import by.it_academy.jd2.dto.MailDto;
import by.it_academy.jd2.entity.EStatus;
import by.it_academy.jd2.entity.MailEntity;
import by.it_academy.jd2.service.api.IMailService;
import by.it_academy.jd2.service.api.IMessageService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MessageService implements IMessageService {

    private final IMailDao mailDao;
    private final IMailService mailService;
    private final ExecutorService executorService;

    public MessageService(IMailDao mailDao, IMailService mailService, ExecutorService executorService) {
        this.mailDao = mailDao;
        this.mailService = mailService;
        this.executorService = executorService;
    }


    public Long create(MailDto mailDto) {
        MailEntity mailEntity = mapToEntity(mailDto);
        log.info("create entity mailEntity : {}", mailEntity);

        MailEntity mailSave = mailDao.save(mailEntity);             // loaded
        Long id = mailSave.getId();
        log.info("save entity mailEntity with id = {} and set status LOADED", id);


        try {

            executorService.submit(() -> sendMail(mailSave));

        } catch (Exception e) {
            mailDao.updateStatus(mailSave, EStatus.ERROR);   //статус поменять
            log.error("entity mailEntity с id = {} and set status ERROR, : ", id, e);
            throw new RuntimeException();
        }
        return id;

    }

    private void sendMail(MailEntity mail) {
        try {
            mailDao.updateStatus(mail, EStatus.SEND);
            log.info("entity mailEntity with id = {} and set status SEND", mail.getId());

            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            log.info("send entity mailEntity with id = {} in THREAD: {}", mail.getId(), Thread.currentThread().getName());
            mailService.send(mail);

            mailDao.updateStatus(mail, EStatus.FINISH);
            log.info("entity mailEntity with id = {} and set status FINISH", mail.getId());

        } catch (Exception e) {
            mailDao.updateStatus(mail, EStatus.ERROR);
            log.info("entity mailEntity with id = {} and set status ERROR", mail.getId());
        }
    }

    public List<MailEntity> getAll() {
        return mailDao.getAll();
    }

    public String getAllJson() {
        List<MailEntity> all = getAll();

        return new Gson().toJson(all);
    }

    private MailEntity mapToEntity(MailDto mailDto) {
        return MailEntity.builder()
                .body(mailDto.getBody())
                .title(mailDto.getTitle())
                .recipient(mailDto.getRecipient())
                .createAt(LocalDateTime.now())
                .status(EStatus.LOADED)
                .build();
    }

}
