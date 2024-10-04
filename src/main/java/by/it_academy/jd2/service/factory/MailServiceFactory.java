package by.it_academy.jd2.service.factory;

import by.it_academy.jd2.service.MailService;

public class MailServiceFactory {
    private static final MailService instance = new MailService(
            "smtp.mail.ru",
            "genia-genius-91@mail.ru",
            System.getenv("ENV_PASSWORD")
    );

    private MailServiceFactory() {}

    public static MailService getInstance() {
        return instance;
    }
}
