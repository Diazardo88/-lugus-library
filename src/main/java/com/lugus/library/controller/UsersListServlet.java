package com.lugus.library.controller;

import com.lugus.library.model.User;
import com.lugus.library.model.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UsersListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            resp.sendRedirect("main.jsp");
            return;
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        UserDAO dao = new UserDAO();
        List<User> users = dao.getAllUsers();

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (i > 0) json.append(",");
            json.append("{");
            json.append("\"id\":").append(u.getId()).append(",");
            json.append("\"email\":\"").append(escapeJson(u.getEmail())).append("\",");
            json.append("\"username\":\"").append(escapeJson(u.getUsername() != null ? u.getUsername() : "")).append("\",");
            json.append("\"role\":\"").append(escapeJson(u.getRole())).append("\",");
            json.append("\"blocked\":").append(u.isBlocked()).append(",");
            json.append("\"blockReason\":\"").append(escapeJson(u.getBlockReason() != null ? u.getBlockReason() : "")).append("\",");
            json.append("\"blockUntil\":\"").append(escapeJson(u.getBlockUntil() != null ? u.getBlockUntil() : "")).append("\",");
            json.append("\"createdAt\":\"").append(escapeJson(u.getCreatedAt())).append("\"");
            json.append("}");
        }
        json.append("]");
        out.print(json.toString());
    }

    private String escapeJson(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}
