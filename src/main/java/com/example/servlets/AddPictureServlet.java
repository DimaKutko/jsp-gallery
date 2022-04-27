package com.example.servlets;

import com.example.dao.PictureDao;
import com.example.orm.Picture;
import com.example.orm.User;
import com.example.services.Hasher;
import com.example.services.RndService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/AddPicture")
@Singleton
@MultipartConfig()
public class AddPictureServlet extends HttpServlet {
    @Inject
    private Hasher hasher;
    @Inject
    private RndService rnd;
    @Inject
    private PictureDao pictureDao;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Part pic = request.getPart("picture");

            String uploadPathStr = getServletContext().getRealPath(".") + File.separator +
                    ".." + File.separator +
                    ".." + File.separator +
                    "uploads" + File.separator;

            //get name file
            String fileName = pic.getSubmittedFileName();
            int dotPos = fileName.lastIndexOf('.');
            if (dotPos == -1) {
                throw new Exception("file name is not correct");
            }

            //Generate new file name by hash
            String ext = fileName.substring(dotPos);
            String savedFileName = fileName;
            File savedFile;
            do {
                savedFileName = hasher.hash(savedFileName + rnd.getRandomInt()) + ext;
                savedFile = new File(uploadPathStr + savedFileName);
            } while (savedFile.isFile());

            //Upload to db file name
            pictureDao.addPicture(
                    new Picture(request.getParameter("description"),
                            savedFileName,
                            ((User) request.getAttribute("user")).getId())
            );
        } catch (Exception ex) {
            System.out.println("AddPictureServlet error: " + ex.toString());
        }

        response.sendRedirect(request.getContextPath());
    }
}
