package com.example.ioc;

import com.example.filters.AuthFilter;
import com.example.filters.DbFilter;
import com.example.servlets.*;
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
                        // Filters
                        filter("/*").through(DbFilter.class);
                        filter("/*").through(AuthFilter.class);

                        // Servlets
                        serve("/").with(HomeServlet.class);
                        serve("/AddPicture").with(AddPictureServlet.class);
                        serve("/GetPicture/*").with(GetPictureServlet.class);
                        serve("/EditPicture").with(EditPictureServlet.class);
                        serve("/api/picture").with(ApiPictureServlet.class);
                    }
                }, new RegisterServices()
        );
    }
}
