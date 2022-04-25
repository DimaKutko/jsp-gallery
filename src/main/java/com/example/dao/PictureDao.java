package com.example.dao;

import com.example.orm.Picture;

import java.sql.*;
import java.util.ArrayList;

//Data access for Gallery table
public class PictureDao {
    private Connection con;

    public PictureDao(Connection con) {
        this.con = con;
    }

    public ArrayList<Picture> getPicturesList() {
        ArrayList<Picture> ret = new ArrayList<>();


        try (Statement ps = con.createStatement()) {

            String query = "SELECT * FROM Gallery";

            try (ResultSet res = ps.executeQuery(query)) {
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

        try (PreparedStatement ps = con.prepareStatement(query)) {
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

