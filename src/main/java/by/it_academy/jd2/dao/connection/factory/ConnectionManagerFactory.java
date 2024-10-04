package by.it_academy.jd2.dao.connection.factory;

import by.it_academy.jd2.dao.connection.ConnectionManager;
import by.it_academy.jd2.dao.connection.api.IConnectionManager;

public class ConnectionManagerFactory {
    private static final IConnectionManager instance = new ConnectionManager();

    private ConnectionManagerFactory() {}

    public static IConnectionManager getInstance() {
        return instance;
    }
}
