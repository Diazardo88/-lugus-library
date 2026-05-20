package com.lugus.library.controller;

import com.lugus.library.model.Document;
import com.lugus.library.model.DocumentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/document")
public class ViewDocumentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        DocumentDAO dao = new DocumentDAO();
        Document doc = dao.getDocumentById(id);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (doc != null) {
            out.print("{\"id\":" + doc.getId() +
                ",\"title\":\"" + escapeJson(doc.getTitle()) + "\",\"author\":\"" + escapeJson(doc.getAuthor()) + "\",\"info\":\"" + escapeJson(doc.getInfo() != null ? doc.getInfo() : "") + "\",\"filename\":\"" + escapeJson(doc.getFilename()) + "\",\"createdAt\":\"" + escapeJson(doc.getCreatedAt()) + "\",\"uploadedBy\":" + doc.getUploadedBy() + ",\"uploaderName\":\"" + escapeJson(doc.getUploaderName() != null ? doc.getUploaderName() : "") + "\",\"uploaderEmail\":\"" + escapeJson(doc.getUploaderEmail() != null ? doc.getUploaderEmail() : "") + "\"}");
        } else {
            out.print("{\"error\": \"Documento no encontrado\"}");
        }
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}
