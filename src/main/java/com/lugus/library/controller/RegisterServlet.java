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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String username = req.getParameter("username");

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        if (!password.equals(confirmPassword)) {
            out.print("{\"success\": false, \"error\": \"Las contraseñas no coinciden\"}");
            return;
        }

        UserDAO dao = new UserDAO();
        if (dao.emailExists(email)) {
            out.print("{\"success\": false, \"error\": \"El correo ya está registrado\"}");
            return;
        }

        if (dao.register(email, password, username)) {
            User user = dao.authenticate(email, password);
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userName", user.getUsername() != null ? user.getUsername() : email.split("@")[0]);
            out.print("{\"success\": true}");
        } else {
            out.print("{\"success\": false, \"error\": \"Error al registrarse\"}");
        }
    }
}
