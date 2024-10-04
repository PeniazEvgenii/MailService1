package by.it_academy.jd2.dao;


import by.it_academy.jd2.dao.api.IMailDao;
import by.it_academy.jd2.dao.connection.api.IConnectionManager;
import by.it_academy.jd2.dao.connection.factory.ConnectionManagerFactory;
import by.it_academy.jd2.dao.exception.DaoException;
import by.it_academy.jd2.entity.EStatus;
import by.it_academy.jd2.entity.MailEntity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MailDao implements IMailDao {
    private static final String INSERT_MAIL_SQL = "INSERT INTO app.mails (email, title, body, create_at, status, status_at) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_MAIL_SQL = "SELECT id, email, title, body, create_at, status FROM app.mails " +
            "ORDER BY create_at DESC";
    private static final String UPDATE_MAIL_SQL = "UPDATE app.mails SET email=?, title=?, body=?, create_at=?, status=?, status_at = ? " + //crete не обновлякм
            "WHERE id=?";


    private final IConnectionManager connectionManager;

    public MailDao(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public MailEntity save(MailEntity mailEntity) {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MAIL_SQL, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setObject(1, mailEntity.getRecipient());
            preparedStatement.setObject(2, mailEntity.getTitle());
            preparedStatement.setObject(3, mailEntity.getBody());
            preparedStatement.setObject(4, mailEntity.getCreateAt());
            preparedStatement.setObject(5, mailEntity.getStatus().name());
            preparedStatement.setObject(6, LocalDateTime.now());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                mailEntity.setId(generatedKeys.getObject("id", Long.class));
            }
            return mailEntity;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<MailEntity> getAll() {
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MAIL_SQL);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<MailEntity> listMails = new ArrayList<>();
            while (resultSet.next()) {
                MailEntity mailEntity = new MailEntity();
                mailEntity.setId(resultSet.getObject("id", Long.class));
                mailEntity.setBody(resultSet.getObject("body", String.class));
                mailEntity.setTitle(resultSet.getObject("title", String.class));
                mailEntity.setRecipient(resultSet.getObject("email", String.class));
                mailEntity.setCreateAt(resultSet.getObject("create_at", LocalDateTime.class));
                mailEntity.setStatus(EStatus.getStatus(
                        resultSet.getObject("status", String.class))
                        .orElse(null));
                listMails.add(mailEntity);
            }
            return listMails;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public MailEntity updateStatus(MailEntity mailEntity, EStatus status) {       //думать или void
        try (Connection connection = connectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MAIL_SQL);
        ) {
            preparedStatement.setObject(1, mailEntity.getRecipient());
            preparedStatement.setObject(2, mailEntity.getTitle());
            preparedStatement.setObject(3, mailEntity.getBody());
            preparedStatement.setObject(4, mailEntity.getCreateAt());
            preparedStatement.setObject(5, status.name());
            preparedStatement.setObject(6, LocalDateTime.now());
            preparedStatement.setObject(7, mailEntity.getId());
            preparedStatement.executeUpdate();
            mailEntity.setStatus(EStatus.getStatus(status.name()).orElse(null));
            return mailEntity;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

}

