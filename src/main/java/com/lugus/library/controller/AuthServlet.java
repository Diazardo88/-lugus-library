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

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        User user = dao.authenticate(email, password);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userName", user.getUsername() != null ? user.getUsername() : user.getEmail().split("@")[0]);
            out.print("{\"success\": true, \"role\": \"" + user.getRole() + "\"}");
        } else {
            out.print("{\"success\": false, \"error\": \"Correo o contraseña inválidos o cuenta bloqueada\"}");
        }
    }
}
