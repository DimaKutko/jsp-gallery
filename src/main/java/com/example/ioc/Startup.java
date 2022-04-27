package com.example.ioc;

import com.example.filters.AuthFilter;
import com.example.filters.DbFilter;
import com.example.servlets.AddPictureServlet;
import com.example.servlets.HomeServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

@Singleton
public class Startup extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        // Configuration servlets and filters
                        filter("/*").through(DbFilter.class);
                        filter("/*").through(AuthFilter.class);

                        serve("/").with(HomeServlet.class);
                        serve("/AddPicture").with(AddPictureServlet.class);
                    }
                }, new RegisterServices()
        );
    }
}
