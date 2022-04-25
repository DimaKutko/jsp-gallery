package com.example.filters;

import com.example.dao.PictureDao;
import com.example.dao.UserDao;

import javax.servlet.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbFilter implements Filter {
    FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Connection connection;

        try {
            String connectionString = "jdbc:mysql://localhost:3306/gallery"
                    + "?useUnicode=true&characterEncoding=UTF-8"
                    + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, "gallery_user", "gallery_password");

            //Temporary GetIt
            servletRequest.setAttribute("userDao", new UserDao(connection));
            servletRequest.setAttribute("pictureDao", new PictureDao(connection));

            filterChain.doFilter(servletRequest, servletResponse);

            //After works filters
            connection.close();
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

