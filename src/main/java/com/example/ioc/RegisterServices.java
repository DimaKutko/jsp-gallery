package com.example.ioc;

import com.example.services.*;
import com.google.inject.AbstractModule;

public class RegisterServices extends AbstractModule {

    @Override
    protected void configure() {
        bind(DbConnector.class).to(MySqlDbConnector.class);
        bind(Hasher.class).to(Sha2Hasher.class);

        bind(RndService.class);
    }
}

