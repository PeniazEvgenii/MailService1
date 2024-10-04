package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.dao.factory.MailDaoFactory;
import by.it_academy.jd2.service.MessageService;
import by.it_academy.jd2.service.api.IMessageService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageServiceFactory {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private static final IMessageService instance = new MessageService(
            MailDaoFactory.getInstance(),
            MailServiceFactory.getInstance(),
            executorService);

    private MessageServiceFactory() {}

    public static IMessageService getInstance() {
        return instance;
    }
}
