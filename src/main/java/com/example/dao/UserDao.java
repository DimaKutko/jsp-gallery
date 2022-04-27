package com.example.dao;

import com.example.orm.User;
import com.example.services.DbConnector;
import com.example.services.Hasher;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Singleton
public class UserDao {
    private final Connection connection;
    private final Hasher hasher;

    @Inject
    public UserDao(DbConnector connector, Hasher hasher) {
        this.connection = connector.getConnection();
        this.hasher = hasher;
    }

    public boolean addUser(String login, String pass) {
        if (!isLoginFree(login)) {
            System.out.println("addUser error: Login in use");
            return false;
        }

        String query = "INSERT Into Users(id, login, pass_hash, pass_salt) VALUES (UUID(), ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            String saltHash = hasher.hash(new Date().getTime() + "");
            String passHash = hasher.hash(pass + saltHash);

            ps.setString(1, login);
            ps.setString(2, passHash);
            ps.setString(3, saltHash);

            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }

        return true;
    }


    public boolean isLoginFree(String login) {
        String query = "SELECT  COUNT(id) FROM Users WHERE login = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, login);

            ResultSet res = ps.executeQuery();
            res.next();
            if (res.getInt(1) > 0) {
                System.out.println("Login in use");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("isLoginFree error: " + ex.getMessage());
            return false;
        }

        return true;
    }

    public User getUserByID(String uid) {
        String query = "SELECT u.login, u.pass_salt FROM Users u WHERE u.id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, uid);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                return new User(uid, res.getString(1), res.getString(2));
            }

            System.out.println("id not used");
        } catch (Exception ex) {
            System.out.println("getUserByID error: " + ex.getMessage());
        }

        return null;
    }

    public User getUserByCredentials(String login, String pass) {
        String query = "SELECT u.id, u.pass_hash, u.pass_salt FROM Users u WHERE u.login = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, login);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                if (hasher.hash(pass + res.getString(3)).equals(res.getString(2))) {
                    return new User(res.getString(1), login, res.getString(3));
                }
            }

            throw new Exception("Login not used");
        } catch (Exception ex) {
            System.out.println("getUserByCredentials error: " + ex.getMessage());
        }
        return null;
    }
}