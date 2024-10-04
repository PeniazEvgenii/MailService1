package by.it_academy.jd2.controller.listener;

import by.it_academy.jd2.dao.connection.factory.ConnectionManagerFactory;
import by.it_academy.jd2.service.factory.ExecutorServiceFactory;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@WebListener()
@Slf4j
public class ContextListener implements ServletContextListener {


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ExecutorServiceFactory.closeExecutorService();
        log.info("close executorService");
        ConnectionManagerFactory.getInstance().close();
        log.info("close ConnectionManager c3po");
    }
}
