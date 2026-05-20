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
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        DocumentDAO dao = new DocumentDAO();
        List<Document> docs = dao.searchDocuments(query);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < docs.size(); i++) {
            Document doc = docs.get(i);
            if (i > 0) json.append(",");
            json.append("{");
            json.append("\"id\":").append(doc.getId()).append(",");
            json.append("\"title\":\"").append(escapeJson(doc.getTitle())).append("\",");
            json.append("\"author\":\"").append(escapeJson(doc.getAuthor())).append("\",");
            json.append("\"info\":\"").append(escapeJson(doc.getInfo() != null ? doc.getInfo() : "")).append("\",");
            json.append("\"filename\":\"").append(escapeJson(doc.getFilename())).append("\",");
            json.append("\"createdAt\":\"").append(escapeJson(doc.getCreatedAt())).append("\"");
            json.append("}");
        }
        json.append("]");
        out.print(json.toString());
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}
