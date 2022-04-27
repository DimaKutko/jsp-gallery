package com.example.dao;

import com.example.orm.Picture;
import com.example.services.DbConnector;
import com.example.services.Hasher;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.*;
import java.util.ArrayList;

//Data access for Gallery table
@Singleton
public class PictureDao {
    private Connection connector;

    @Inject
    public PictureDao(DbConnector connector, Hasher hasher) {
        this.connector = connector.getConnection();
    }

    public ArrayList<Picture> getPicturesList() {
        ArrayList<Picture> ret = new ArrayList<>();


        try (Statement cs = connector.createStatement()) {

            String query = "SELECT * FROM Gallery";

            try (ResultSet res = cs.executeQuery(query)) {
                while (res.next()) {
                    ret.add(new Picture(res));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error getPicturesList: " + ex.getMessage());
        }

        return ret;
    }

    public ArrayList<Picture> getPicturesListByUserUID(String userUID) {
        ArrayList<Picture> ret = new ArrayList<>();

        String query = "SELECT * FROM Gallery WHERE user_id = ?";

        try (PreparedStatement ps = connector.prepareStatement(query)) {
            ps.setString(1, userUID);

            try (ResultSet res = ps.executeQuery()) {
                while (res.next()) {
                    ret.add(new Picture(res));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error getPicturesList: " + ex.getMessage());
        }

        return ret;
    }

    public boolean addPicture(Picture picture) {
        if (picture == null) return false;
        String query = "INSERT INTO Gallery(id, description, picture, user_id, moment, deleted)" +
                "VALUES(UUID(), ?, ?, ?, CURRENT_TIMESTAMP, NULL)";

        try (PreparedStatement ps = connector.prepareStatement(query)) {
            ps.setString(1, picture.getDescription());
            ps.setString(2, picture.getPicture());
            ps.setString(3, picture.getUserId());

            ps.executeUpdate();

            return true;
        } catch (Exception ex) {
            System.out.println("Error addPicture: " + ex.getMessage());
        }

        return false;
    }
}

