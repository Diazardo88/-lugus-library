package com.lugus.library.controller;

import com.lugus.library.model.Document;
import com.lugus.library.model.DocumentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete")
public class DeleteDocumentServlet extends HttpServlet {
    private static final String UPLOAD_DIR = System.getenv("UPLOAD_DIR") != null
        ? System.getenv("UPLOAD_DIR")
        : System.getProperty("catalina.base") + File.separator + "uploads";

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            out.print("{\"success\": false, \"error\": \"No autorizado\"}");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        String reason = req.getParameter("reason");
        String logMsg = reason != null && !reason.isEmpty() ? " (razón: " + reason + ")" : "";

        DocumentDAO dao = new DocumentDAO();
        Document doc = dao.getDocumentById(id);

        if (dao.deleteDocument(id)) {
            if (doc != null && doc.getFilename() != null) {
                File file = new File(UPLOAD_DIR + File.separator + doc.getFilename());
                if (file.exists()) file.delete();
            }
            System.out.println("Documento " + id + " eliminado por admin" + logMsg);
            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"error\": \"Error al eliminar\"}");
        }
    }
}
