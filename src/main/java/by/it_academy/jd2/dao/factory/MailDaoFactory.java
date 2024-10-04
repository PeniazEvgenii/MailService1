package by.it_academy.jd2.dao.factory;

import by.it_academy.jd2.dao.MailDao;
import by.it_academy.jd2.dao.api.IMailDao;
import by.it_academy.jd2.dao.connection.factory.ConnectionManagerFactory;

public class MailDaoFactory {
    private static final IMailDao instance = new MailDao(ConnectionManagerFactory.getInstance());

    private MailDaoFactory() {}

    public static IMailDao getInstance() {
        return instance;
    }
}
