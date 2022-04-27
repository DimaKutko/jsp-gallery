package com.example.servlets;

import com.example.dao.PictureDao;
import com.example.orm.Picture;
import com.example.orm.User;
import com.example.services.Hasher;
import com.example.services.RndService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String uploadPathStr = getServletContext().getRealPath(".") +
                    File.separator + ".." +
                    File.separator + ".." +
                    File.separator + "upload" +
                    File.separator;

            // Get name file
            Part pic = request.getPart("picture");
            String fileName = pic.getSubmittedFileName();
            int dotPos = fileName.lastIndexOf('.');
            if (dotPos == 1) {
                throw new Exception("file name is not correct");
            }

            // Generate new file name by hash
            String ext = fileName.substring(dotPos);
            String savedFileName = fileName;
            File savedFile;
            do {
                savedFileName = hasher.hash(savedFileName + rnd.getRandomInt()) + ext;
                savedFile = new File(uploadPathStr + savedFileName);
            } while (savedFile.isFile());

            // Copy file to upload folder
            Path uploadPath = Paths.get(uploadPathStr + savedFileName);
            Files.copy(pic.getInputStream(), uploadPath);

            // Upload to db file name
            pictureDao.addPicture(new Picture(
                    request.getParameter("description"),
                    savedFileName,
                    ((User) request.getAttribute("user")).getId())
            );
        } catch (Exception ex) {
            System.out.println("AddPictureServlet error: " + ex.getMessage());
        }

        response.sendRedirect(request.getContextPath());
    }
}
