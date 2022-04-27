package com.example.servlets;

import com.example.dao.PictureDao;
import com.example.dao.UserDao;
import com.example.orm.Picture;
import com.example.orm.PictureView;
import com.example.orm.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Singleton
public class HomeServlet extends HttpServlet {

    @Inject
    UserDao userDao;
    @Inject
    PictureDao pictureDao;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ArrayList<PictureView> pictures = new ArrayList<>();

            // Check is authed user
            User currentUser = (User) request.getAttribute("user");
            String pathPrefix = request.getContextPath() + "/GetPicture/";
            if (currentUser == null) {
                // Get all picture
                pictures = reformatPictures(pictureDao.getPicturesList(), pathPrefix);
            } else {
                // Get picture only current user
                pictures = reformatPictures(pictureDao.getPicturesListByUserUID(currentUser.getId()), pathPrefix);
            }

            request.setAttribute("pictures", pictures.toArray(new PictureView[0]));
        } catch (Exception ex) {
            System.out.println("HomeServlet error: " + ex.getMessage());
        }

        request.getRequestDispatcher("index.jsp")
                .forward(request, response);
    }


    private ArrayList<PictureView> reformatPictures(ArrayList<Picture> originalPictures, String pathPrefix) throws Exception {
        ArrayList<PictureView> pictures = new ArrayList<>();
        // Reformat to PictureView
        if (originalPictures != null) {
            ArrayList<User> users = new ArrayList<>();

            for (Picture picture : originalPictures) {
                // Find user for get login
                // Get user from state
                User user = users.stream().filter(u -> picture.getUserId().equals(u.getId())).findFirst().orElse(null);
                // Get user from db
                if (user == null) user = userDao.getUserByID(picture.getUserId());
                if (user == null) throw new Exception("user not found");
                users.add(user);

                // Reformat
                pictures.add(new PictureView(
                        pathPrefix + picture.getPicture(),
                        picture.getDescription(),
                        user.getLogin()
                ));
            }
        }

        return pictures;
    }
}