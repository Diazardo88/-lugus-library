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

@WebServlet("/block")
public class BlockUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("userRole"))) {
            out.print("{\"success\": false, \"error\": \"No autorizado\"}");
            return;
        }

        int userId = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");

        UserDAO dao = new UserDAO();
        boolean success;
        if ("block".equals(action)) {
            String reason = req.getParameter("reason");
            String until = req.getParameter("until");
            success = dao.blockUser(userId, reason, until);
        } else {
            success = dao.unblockUser(userId);
        }

        out.print("{\"success\": " + success + "}");
    }
}
