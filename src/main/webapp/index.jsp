<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("userRole") != null) {
        response.sendRedirect("main.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Lugus - Acceso</title>
    <link href="https://fonts.googleapis.com/css2?family=Uncial+Antiqua&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="login-page">
    <div class="login-container">
        <div class="login-logo">
            <img src="images/logo-icon.svg" alt="Logo">
        </div>
        <h1 class="library-title">Biblioteca Lugus</h1>
        <p class="library-tagline">Acceda a documentos y libros polémicos a lo largo de la historia.</p>

        <div class="tabs">
            <button class="tab-btn active" id="loginTab">Iniciar Sesión</button>
            <button class="tab-btn" id="registerTab">Registrarse</button>
        </div>

        <form id="loginForm" class="auth-form active">
            <label for="loginEmail">Correo electrónico</label>
            <input type="email" id="loginEmail" name="email" required>

            <label for="loginPassword">Contraseña</label>
            <div class="password-wrapper">
                <input type="password" id="loginPassword" name="password" required>
                <button type="button" class="toggle-password" data-target="loginPassword" aria-label="Mostrar contraseña">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                </button>
            </div>

            <button type="submit">Iniciar Sesión</button>
        </form>

        <form id="registerForm" class="auth-form">
            <label for="regUsername">Nombre de usuario</label>
            <input type="text" id="regUsername" name="username" required>

            <label for="regEmail">Correo electrónico</label>
            <input type="email" id="regEmail" name="email" required>

            <label for="regPassword">Contraseña</label>
            <div class="password-wrapper">
                <input type="password" id="regPassword" name="password" required>
                <button type="button" class="toggle-password" data-target="regPassword" aria-label="Mostrar contraseña">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                </button>
            </div>

            <label for="regConfirm">Confirmar contraseña</label>
            <div class="password-wrapper">
                <input type="password" id="regConfirm" name="confirmPassword" required>
                <button type="button" class="toggle-password" data-target="regConfirm" aria-label="Mostrar contraseña">
                    <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                </button>
            </div>

            <button type="submit">Registrarse</button>
        </form>
    </div>

    <script src="js/app.js"></script>
</body>
</html>
