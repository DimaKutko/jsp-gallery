package com.example.dao;

import com.example.orm.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class UserDao {
    private Connection con;

    public UserDao(Connection con) {
        this.con = con;
    }

    public boolean addUser(String login, String pass) {
        if (!isLoginFree(login)) {
            System.out.println("addUser error: Login in use");
            return false;
        }

        String query = "INSERT Into Users(id, login, pass_hash, pass_salt) VALUES (UUID(), ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(query)) {
            String saltHash = hash(new Date().getTime() + "");
            String passHash = hash(pass + saltHash);

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

    public String hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean isLoginFree(String login) {
        String query = "SELECT  COUNT(id) FROM Users WHERE login = ?";

        try (PreparedStatement ps = con.prepareStatement(query)) {
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

        try (PreparedStatement ps = con.prepareStatement(query)) {
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

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, login);
            ResultSet res = ps.executeQuery();

            if (res.next()) {
                if (hash(pass + res.getString(3)).equals(res.getString(2))) {
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