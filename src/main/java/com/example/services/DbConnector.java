package com.example.services;

import java.sql.Connection;

public interface DbConnector {
    Connection getConnection();
}

