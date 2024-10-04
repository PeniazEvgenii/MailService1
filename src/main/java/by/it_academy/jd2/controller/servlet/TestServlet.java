package by.it_academy.jd2.controller.servlet;

import by.it_academy.jd2.controller.servlet.delete.Order;
import by.it_academy.jd2.controller.servlet.delete.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/json/test")
public class TestServlet extends HttpServlet {
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream();
        UserDto userDto = objectMapper.readValue(inputStream, UserDto.class);

        resp.setContentType("application/json");

        resp.getWriter().write(objectMapper.writeValueAsString(userDto));


    }



}
