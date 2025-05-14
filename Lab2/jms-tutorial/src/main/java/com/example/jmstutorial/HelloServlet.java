package com.example.jmstutorial;

import java.io.*;

import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/send")
public class HelloServlet extends HttpServlet {

    @Resource(lookup = "java:/jms/queue/TestQueue")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String message = req.getParameter("message");

        if (message == null || message.trim().isEmpty()) {
            resp.getWriter().write("Message is empty. Please enter a message.");
            return;
        }

        try (JMSContext context = connectionFactory.createContext()) {
            context.createProducer().send(queue, message);
            resp.getWriter().write("Message sent to queue: " + message);
        }
    }
}