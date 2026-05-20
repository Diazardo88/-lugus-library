package com.lugus.library.controller;

import com.lugus.library.model.User;
import com.lugus.library.model.UserDAO;
import com.lugus.library.model.Document;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        int profileUserId;
        String paramId = req.getParameter("id");
        if (paramId != null && !paramId.isEmpty()) {
            profileUserId = Integer.parseInt(paramId);
        } else {
            profileUserId = (int) session.getAttribute("userId");
        }

        UserDAO dao = new UserDAO();
        User user = dao.getUserById(profileUserId);
        if (user == null) {
            resp.sendRedirect("main.jsp");
            return;
        }

        List<Document> userDocs = dao.getDocumentsByUser(profileUserId);

        req.setAttribute("profileUser", user);
        req.setAttribute("userDocs", userDocs);
        req.getRequestDispatcher("profile.jsp").forward(req, resp);
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
        String username = req.getParameter("username");

        UserDAO dao = new UserDAO();
        if (dao.updateProfile(userId, username, null, null)) {
            session.setAttribute("userName", username);
            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"error\": \"Error al actualizar perfil\"}");
        }
    }
}
