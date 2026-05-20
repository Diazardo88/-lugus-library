package com.lugus.library.controller;

import com.lugus.library.model.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            out.print("{\"success\": false, \"error\": \"No autorizado\"}");
            return;
        }

        int userId = Integer.parseInt(req.getParameter("id"));
        String reason = req.getParameter("reason");
        String logMsg = reason != null && !reason.isEmpty() ? " (razón: " + reason + ")" : "";

        UserDAO dao = new UserDAO();

        if (dao.deleteUser(userId)) {
            System.out.println("Usuario " + userId + " eliminado por admin" + logMsg);
            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"error\": \"No se puede eliminar administradores\"}");
        }
    }
}
