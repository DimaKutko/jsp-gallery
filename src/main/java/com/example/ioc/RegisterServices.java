package com.example.ioc;

import com.example.services.DbConnector;
import com.example.services.MySqlDbConnector;
import com.example.services.RndService;
import com.google.inject.AbstractModule;

public class RegisterServices extends AbstractModule {

    @Override
    protected void configure() {
        bind(DbConnector.class).to(MySqlDbConnector.class);

        bind(RndService.class);
    }
}

