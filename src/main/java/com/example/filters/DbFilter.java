package com.example.filters;

import com.example.dao.PictureDao;
import com.example.dao.UserDao;
import com.example.services.DbConnector;
import com.example.services.RndService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class DbFilter implements Filter {
    FilterConfig filterConfig;

    @Inject
    DbConnector connector;

    @Inject
    RndService rnd;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Connection connection;

        try {
            servletRequest.setAttribute("rnd1", rnd.num);

            //Grt connection
            connection = connector.getConnection();

            //Temporary GetIt
            servletRequest.setAttribute("userDao", new UserDao(connection));
            servletRequest.setAttribute("pictureDao", new PictureDao(connection));

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            servletRequest.getRequestDispatcher("static.html")
                    .forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}

