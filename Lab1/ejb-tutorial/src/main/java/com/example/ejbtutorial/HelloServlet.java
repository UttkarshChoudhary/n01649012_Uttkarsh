package com.example.ejbtutorial;

import com.example.ejb.CalculatorBean;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @EJB
    private CalculatorBean calculatorBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int resulta = calculatorBean.add(10, 5);
        response.getWriter().println("10 + 5 = " + resulta);
        int resultm = calculatorBean.multiply(10, 5);
        response.getWriter().println("10 * 5 = " + resultm);
        int resultd = calculatorBean.divide(10, 5);
        response.getWriter().println("10 / 5 = " + resultd);
        int results = calculatorBean.subtract(10, 5);
        response.getWriter().println("10 - 5 = " + results);
    }
}
