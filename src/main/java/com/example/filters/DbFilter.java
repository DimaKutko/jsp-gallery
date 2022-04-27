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

@Singleton
public class DbFilter implements Filter {
    FilterConfig filterConfig;
    @Inject
    DbConnector connector;
    @Inject
    RndService rnd;
    @Inject
    UserDao userDao;
    @Inject
    PictureDao pictureDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //Set encoding
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");

        Connection connection;

        try {
            servletRequest.setAttribute("rnd1", rnd.num);

            //Grt connection
            connection = connector.getConnection();

            if (connection == null) throw new Exception("DBConnection is null");

            //Temporary GetIt
            servletRequest.setAttribute("userDao", userDao);
            servletRequest.setAttribute("pictureDao", pictureDao);

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            System.out.println("DbFilter error: " + ex.getMessage());
            servletRequest.getRequestDispatcher("static.html")
                    .forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}

