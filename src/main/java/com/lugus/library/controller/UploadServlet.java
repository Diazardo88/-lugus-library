package com.lugus.library.controller;

import com.lugus.library.model.Document;
import com.lugus.library.model.DocumentDAO;
import com.lugus.library.model.User;
import com.lugus.library.model.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100)
public class UploadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR") != null
        ? System.getenv("UPLOAD_DIR")
        : System.getProperty("catalina.base") + File.separator + "uploads";

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            out.print("{\"success\": false, \"error\": \"No autorizado\"}");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        UserDAO userDao = new UserDAO();
        User currentUser = userDao.getUserById(userId);
        if (currentUser != null && currentUser.isBlocked()) {
            String blockMsg = "Tu cuenta est\u00e1 bloqueada";
            if (currentUser.getBlockReason() != null) {
                blockMsg += ". Motivo: " + currentUser.getBlockReason();
            }
            if (currentUser.getBlockUntil() != null && !"permanente".equals(currentUser.getBlockUntil())) {
                blockMsg += ". Hasta: " + currentUser.getBlockUntil();
            } else if (currentUser.getBlockUntil() != null && "permanente".equals(currentUser.getBlockUntil())) {
                blockMsg += " permanentemente";
            }
            out.print("{\"success\": false, \"blocked\": true, \"error\": \"" + escapeJson(blockMsg) + "\"}");
            return;
        }

        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String info = req.getParameter("info");
        Part filePart = req.getPart("file");

        if (title == null || author == null || filePart == null || title.isEmpty() || author.isEmpty()) {
            out.print("{\"success\": false, \"error\": \"Campos obligatorios faltantes\"}");
            return;
        }

        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        filePart.write(UPLOAD_DIR + File.separator + fileName);

        Document doc = new Document();
        doc.setTitle(title);
        doc.setAuthor(author);
        doc.setInfo(info);
        doc.setFilename(fileName);
        doc.setUploadedBy((int) session.getAttribute("userId"));

        DocumentDAO dao = new DocumentDAO();
        if (dao.addDocument(doc)) {
            out.print("{\"success\": true}");
        } else {
            new File(UPLOAD_DIR + File.separator + fileName).delete();
            out.print("{\"success\": false, \"error\": \"Error de base de datos\"}");
        }
    }
}
