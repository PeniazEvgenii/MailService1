package by.it_academy.jd2.dao.connection;

import by.it_academy.jd2.dao.connection.api.IConnectionManager;
import by.it_academy.jd2.util.PropertiesUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager implements IConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    private final String envConfigUrl = System.getenv("DATABASE_URL");
    private final String envConfigUsername = System.getenv("DATABASE_USERNAME");
    private final String envConfigPassword = System.getenv("DATABASE_PASSWORD");

    private DataSource dataSource;
    private final ComboPooledDataSource cpds = new ComboPooledDataSource();

    public ConnectionManager(){}

     {
        load();
    }

    /**
     * Для получения доступа к базе данных используются параметры (URL, username, password)
     * из файла /resources/application.properties. Для изменения параметров воспользуйтесь
     * переменными среды DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD.
     */
    private void load() {
        try {
            cpds.setDriverClass("org.postgresql.Driver");
        }  catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }

        cpds.setJdbcUrl(getUrl());
        cpds.setUser(getUsername());
        cpds.setPassword(getPassword());

        dataSource = cpds;
    }

    public Connection open() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения",e);
        }
    }

    private String getUrl() {
        return isValidEnvVariables() ? envConfigUrl : PropertiesUtil.get(URL_KEY);
    }

    private String getUsername() {
        return isValidEnvVariables() ? envConfigUsername : PropertiesUtil.get(USERNAME_KEY);
    }

    private String getPassword() {
        return isValidEnvVariables() ? envConfigPassword : PropertiesUtil.get(PASSWORD_KEY);
    }

    private boolean isValidEnvVariables() {
        return envConfigUrl != null && envConfigUsername != null && envConfigPassword != null;
    }

    public void close() {
        cpds.close();
    }
}
