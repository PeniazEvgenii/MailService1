package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.dto.MailDto;
import by.it_academy.jd2.service.api.IMessageService;
import by.it_academy.jd2.service.factory.MessageServiceFactory;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/mail", asyncSupported = true)
public class MailServlet extends HttpServlet {
    IMessageService messageService = MessageServiceFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String allJson = messageService.getAllJson();
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        writer.write(allJson);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String body = req.getParameter("body");
        String recipient = req.getParameter("recipient");

        MailDto mailDto = new MailDto(title, body, recipient);

        AsyncContext asyncContext = req.startAsync();
 //       asyncContext.setTimeout(10000); // Устанавливаем таймаут (опционально)

        asyncContext.start(() ->

        {
            try {
                Long l = messageService.create(mailDto);
                resp.getWriter().write("Сообщение создано с id = " + l);
                resp.setStatus(HttpServletResponse.SC_CREATED);

            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            }  finally {
                asyncContext.complete();
            }
        });


    }
}
