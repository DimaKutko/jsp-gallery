package com.example.servlets;

import com.example.dao.PictureDao;
import com.example.orm.Picture;
import com.example.orm.User;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/EditPicture")
@Singleton
public class EditPictureServlet extends HttpServlet {
    @Inject
    PictureDao pictureDao;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String picId = (String) request.getParameter("pictureID");
        User currentUser = (User) request.getAttribute("user");

        try {
            Picture pic = pictureDao.getPictureById(picId);

            // Checks
            if (pic == null) throw new Exception("Picture id is not correct");
            if (pic.getDeleted() != null) throw new Exception("Picture is deleted");
            if (!pic.getUserId().equals(currentUser.getId())) throw new Exception("User not owner this picture");

            // Delete
            if (request.getParameter("delete") != null) {
                if (!pictureDao.deletePictureById(picId)) throw new Exception("Picture delete error");

                session.setAttribute("message", "Successful deleted");
            }

            //Update
            if (request.getParameter("update") != null) {
                pic.setDescription(request.getParameter("description"));
                if (!pictureDao.updatePictureById(pic)) throw new Exception("Picture update error");

                session.setAttribute("message", "Successful update");
            }
        } catch (Exception ex) {
            System.out.println("EditPictureServlet error: " + ex.getMessage());
            session.setAttribute("error", ex.getMessage());
        }

        response.sendRedirect(request.getContextPath());
    }
}
