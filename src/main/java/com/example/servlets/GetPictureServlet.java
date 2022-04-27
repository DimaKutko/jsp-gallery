package com.example.servlets;

import com.google.inject.Singleton;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Singleton
public class GetPictureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getPathInfo().substring(1);
        if (!fileName.isEmpty()) {
            // Check file
            String uploadPath = getServletContext().getRealPath(".") +
                    File.separator + ".." +
                    File.separator + ".." +
                    File.separator + "upload" +
                    File.separator;

            String filePath = uploadPath + fileName;
            File file = new File(filePath);

            if (file.isFile()) {
                // Set file type
                String mimeType = Files.probeContentType(Paths.get(fileName));
                if (mimeType != null) {
                    response.setContentType(mimeType);
                } else {
                    response.setContentType("application/octet-stream");
                }

                // Return file
                byte[] buf = new byte[512];
                int n;

                FileInputStream reader = new FileInputStream(file);
                while ((n = reader.read(buf)) != -1) {
                    response.getOutputStream().write(buf, 0, n);
                }
                reader.close();
            }
        }
    }
}
