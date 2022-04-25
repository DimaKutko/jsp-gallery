package com.example.services;

import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.DriverManager;

@Singleton
public class MySqlDbConnector implements DbConnector {
    private Connection connection;

    @Override
    public Connection getConnection() {
        try {
            if (connection == null) {
                String connectionString = "jdbc:mysql://localhost:3306/gallery"
                        + "?useUnicode=true&characterEncoding=UTF-8"
                        + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(connectionString, "gallery_user", "gallery_password");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }
}
