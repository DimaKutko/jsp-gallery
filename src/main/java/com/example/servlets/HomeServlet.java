package com.example.servlets;

import com.example.services.RndService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet {

    @Inject
    RndService rnd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("rnd3", rnd.num);

        request.setAttribute("fromServlet", "Works");

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }
}