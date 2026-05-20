package com.lugus.library.controller;

import com.lugus.library.model.Document;
import com.lugus.library.model.DocumentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download")
public class DownloadDocumentServlet extends HttpServlet {
    private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR") != null
        ? System.getenv("UPLOAD_DIR")
        : System.getProperty("catalina.base") + File.separator + "uploads";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        DocumentDAO dao = new DocumentDAO();
        Document doc = dao.getDocumentById(id);

        if (doc == null) {
            resp.sendError(404);
            return;
        }

        File file = new File(UPLOAD_DIR + File.separator + doc.getFilename());
        if (!file.exists()) {
            resp.sendError(404);
            return;
        }

        String disposition = "attachment";
        String viewParam = req.getParameter("view");
        if ("true".equals(viewParam)) {
            disposition = "inline";
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", disposition + "; filename=\"" + doc.getFilename() + "\"");
        resp.setContentLengthLong(file.length());

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = resp.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
