package step.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import step.example.dao.UserDao;
import step.example.orm.AuthData;
import step.example.orm.User;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class AuthFilter implements Filter {

    private static final String _authData = "authData";
    private long MAX_LOGIN_TIME;

    @Override
    public void init(FilterConfig filterConfig) {
        this.MAX_LOGIN_TIME = Long.parseLong(filterConfig.getInitParameter("MAX_LOGIN_TIME"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserDao userDao = (UserDao) servletRequest.getAttribute("userDao");

        if (userDao == null) {
            throw new ServletException("AuthFilter: Auth UserDao is null");
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        // POST (Log In | Log Out)
        if (request.getMethod().toUpperCase(Locale.ROOT).equals("POST")) {
            String userLogin = request.getParameter("userLogin");
            String userPass = request.getParameter("userPass");
            String logout = request.getParameter("logout");


            if (logout != null) {
                // Log Out method
                session.removeAttribute(_authData);

                // Reopen index
                response.sendRedirect(request.getRequestURI());
                return;
            } else if (userLogin != null || userPass != null) {
                // Log In method
                User user = userDao.getUserByCredentials(userLogin, userPass);
                if (user != null) {
                    // Success auth
                    session.setAttribute(_authData, new AuthData(user.getId()).toJson());
                } else {
                    // Failed auth
                    AuthData authData = new AuthData();
                    authData.setError("Not correct login or password");
                    session.setAttribute(_authData, authData.toJson());
                }

                // Reopen index
                response.sendRedirect(request.getRequestURI());
                return;
            }
        }

        // Check AuthData in session
        AuthData authData = AuthData.fromJson((String) session.getAttribute(_authData));
        if (authData != null) {

            // Get user login if uid not null
            if (authData.getUid() != null) {
                long deltaTime = new Date().getTime() - authData.getAuthMoment();
                if (deltaTime > MAX_LOGIN_TIME) {
                    session.removeAttribute(_authData);
                    response.sendRedirect(request.getRequestURI());
                    return;
                }

                request.setAttribute("user", userDao.getUserByID(authData.getUid()));
                authData.updateAuthMoment();
                session.setAttribute(_authData, authData.toJson());
            }

            // Check error
            if (authData.getError() != null) {
                request.setAttribute("error", authData.getError());

                // Show error once
                session.removeAttribute(_authData);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}