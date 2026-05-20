package com.lugus.library.controller;

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

@WebServlet("/profile-upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 20)
public class ProfileUploadServlet extends HttpServlet {
    private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR") != null
        ? System.getenv("UPLOAD_DIR")
        : System.getProperty("catalina.base") + File.separator + "uploads";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            out.print("{\"success\": false, \"error\": \"No autorizado\"}");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String type = req.getParameter("type");
        Part filePart = req.getPart("file");

        if (filePart == null || filePart.getSize() == 0) {
            out.print("{\"success\": false, \"error\": \"No se seleccionó ningún archivo\"}");
            return;
        }

        String ext = "";
        String submittedName = filePart.getSubmittedFileName();
        if (submittedName != null && submittedName.contains(".")) {
            ext = submittedName.substring(submittedName.lastIndexOf("."));
        }
        String fileName = "profile_" + userId + "_" + type + "_" + System.currentTimeMillis() + ext;

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        filePart.write(UPLOAD_DIR + File.separator + fileName);

        UserDAO dao = new UserDAO();
        boolean success;
        if ("avatar".equals(type)) {
            success = dao.updateProfile(userId, null, fileName, null);
        } else if ("banner".equals(type)) {
            success = dao.updateProfile(userId, null, null, fileName);
        } else {
            new File(UPLOAD_DIR + File.separator + fileName).delete();
            out.print("{\"success\": false, \"error\": \"Tipo inválido\"}");
            return;
        }

        if (success) {
            out.print("{\"success\": true, \"filename\": \"" + fileName + "\"}");
        } else {
            new File(UPLOAD_DIR + File.separator + fileName).delete();
            out.print("{\"success\": false, \"error\": \"Error al actualizar perfil\"}");
        }
    }
}
