<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.lugus.library.model.User, com.lugus.library.model.Document, java.util.List" %>
<%
    String userRole = (String) session.getAttribute("userRole");
    Integer currentUserId = (Integer) session.getAttribute("userId");
    if (userRole == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    User profileUser = (User) request.getAttribute("profileUser");
    List<Document> userDocs = (List<Document>) request.getAttribute("userDocs");
    if (profileUser == null) {
        response.sendRedirect("main.jsp");
        return;
    }
    boolean isOwnProfile = currentUserId == profileUser.getId();
    boolean isAdmin = "admin".equals(userRole);
    String username = profileUser.getUsername() != null ? profileUser.getUsername() : profileUser.getEmail().split("@")[0];
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Perfil - <%= username %></title>
    <link href="https://fonts.googleapis.com/css2?family=Uncial+Antiqua&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <header>
        <div class="logo">
            <img src="images/logo-icon.svg" alt="Logo" class="logo-icon">
            <span>Biblioteca Lugus</span>
        </div>
        <nav>
            <% if (isAdmin) { %>
                <button id="adminDashboardBtn" class="admin-btn">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                </button>
            <% } %>
            <a href="main.jsp" id="backToMainBtn" class="profile-link">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
            </a>
            <a href="logout" id="logoutBtn"><img src="images/exit-icon.svg" alt="Cerrar sesión"></a>
        </nav>
    </header>

    <div class="profile-page">
        <div class="profile-cover" id="profileCover">
            <% if (profileUser.getBanner() != null && !profileUser.getBanner().isEmpty()) { %>
                <div class="cover-image" style="background-image: url('uploads/<%= profileUser.getBanner() %>');"></div>
            <% } else { %>
                <div class="cover-image cover-gradient"></div>
            <% } %>
        </div>

        <div class="profile-main">
            <div class="profile-sidebar">
                <div class="profile-avatar-section">
                    <div class="profile-avatar-wrapper">
                        <% if (profileUser.getAvatar() != null && !profileUser.getAvatar().isEmpty()) { %>
                            <img src="uploads/<%= profileUser.getAvatar() %>" alt="Avatar" class="profile-avatar-img">
                        <% } else { %>
                            <div class="profile-avatar-placeholder">
                                <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                            </div>
                        <% } %>
                    </div>
                    <h1 class="profile-name"><%= username %></h1>
                    <p class="profile-email-text"><%= profileUser.getEmail() %></p>
                    <span class="profile-badge <%= profileUser.getRole() %>"><%= "admin".equals(profileUser.getRole()) ? "Administrador" : "Usuario" %></span>
                </div>

                <% if (isAdmin && !isOwnProfile) { %>
                    <div class="profile-admin-section">
                        <h3>Administración</h3>
                        <div class="admin-btn-group">
                            <button id="blockUserBtn" class="btn-admin-block">
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><line x1="4.93" y1="4.93" x2="19.07" y2="19.07"/></svg>
                                <%= profileUser.isBlocked() ? "Desbloquear" : "Bloquear" %>
                            </button>
                            <button id="deleteUserBtn" class="btn-admin-delete">
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/></svg>
                                Eliminar
                            </button>
                        </div>
                    </div>
                <% } %>

                <div class="profile-stats">
                    <div class="stat-item">
                        <span class="stat-number"><%= userDocs.size() %></span>
                        <span class="stat-label">Documentos</span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-number"><%= profileUser.getCreatedAt() != null ? profileUser.getCreatedAt().substring(0, 10) : "" %></span>
                        <span class="stat-label">Miembro desde</span>
                    </div>
                </div>
            </div>

            <div class="profile-content">
                <div class="content-header">
                    <h2>Documentos publicados</h2>
                    <span class="doc-count"><%= userDocs.size() %> documento<%= userDocs.size() != 1 ? "s" : "" %></span>
                </div>
                <div class="profile-docs" id="profileDocs">
                    <% if (userDocs.isEmpty()) { %>
                        <div class="empty-docs">
                            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
                            <p>Este usuario a&uacute;n no ha publicado documentos.</p>
                        </div>
                    <% } else { %>
                        <% for (Document doc : userDocs) { %>
                            <div class="profile-doc-card">
                                <div class="doc-card-icon">
                                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="8" y1="13" x2="16" y2="13"/><line x1="8" y1="17" x2="13" y2="17"/></svg>
                                </div>
                                <div class="profile-doc-info">
                                    <h3><%= doc.getTitle() %></h3>
                                    <p><%= doc.getAuthor() %> &middot; <%= doc.getCreatedAt() != null ? doc.getCreatedAt().substring(0, 10) : "" %></p>
                                </div>
                                <a href="download?id=<%= doc.getId() %>" class="profile-doc-download">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg>
                                    Descargar
                                </a>
                            </div>
                        <% } %>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <!-- Block Modal -->
    <div id="blockModal" class="modal">
        <div class="modal-content block-modal">
            <div class="modal-header">
                <h2><%= profileUser.isBlocked() ? "Desbloquear" : "Bloquear" %> usuario</h2>
                <button class="btn-close-modal" id="closeBlockModal">&times;</button>
            </div>
            <% if (!profileUser.isBlocked()) { %>
                <form id="blockForm">
                    <input type="hidden" id="blockUserId" value="<%= profileUser.getId() %>">
                    <div class="form-group">
                        <label for="blockReason">Raz&oacute;n del bloqueo</label>
                        <textarea id="blockReason" name="reason" rows="3" placeholder="Indica la razón del bloqueo..." required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="blockDuration">Duraci&oacute;n</label>
                        <div class="duration-options">
                            <label class="duration-option">
                                <input type="radio" name="duration" value="1h">
                                <span>1 hora</span>
                            </label>
                            <label class="duration-option">
                                <input type="radio" name="duration" value="1" checked>
                                <span>1 d&iacute;a</span>
                            </label>
                            <label class="duration-option">
                                <input type="radio" name="duration" value="7">
                                <span>7 d&iacute;as</span>
                            </label>
                            <label class="duration-option">
                                <input type="radio" name="duration" value="30">
                                <span>30 d&iacute;as</span>
                            </label>
                            <label class="duration-option">
                                <input type="radio" name="duration" value="permanente">
                                <span>Permanente</span>
                            </label>
                        </div>
                    </div>
                    <div class="form-buttons">
                        <button type="button" class="btn-cancel" id="cancelBlockBtn">Cancelar</button>
                        <button type="submit" class="btn-confirm-block">Bloquear</button>
                    </div>
                </form>
            <% } else { %>
                <form id="unblockForm">
                    <input type="hidden" id="unblockUserId" value="<%= profileUser.getId() %>">
                    <p class="unblock-info">Este usuario est&aacute; actualmente bloqueado. ¿Deseas desbloquearlo?</p>
                    <% if (profileUser.getBlockReason() != null) { %>
                        <div class="block-reason-display">
                            <strong>Raz&oacute;n:</strong> <%= profileUser.getBlockReason() %>
                        </div>
                    <% } %>
                    <div class="form-buttons">
                        <button type="button" class="btn-cancel" id="cancelUnblockBtn">Cancelar</button>
                        <button type="submit" class="btn-confirm-unblock">Desbloquear</button>
                    </div>
                </form>
            <% } %>
        </div>
    </div>

    <footer class="site-footer">
        <div class="footer-content">
            <p>&copy; 2026 Biblioteca Lugus. Todos los derechos reservados.</p>
            <p class="footer-tagline">Conocimiento sin fronteras, verdad sin censura.</p>
        </div>
    </footer>

    <script>
        window.profileUserId = <%= profileUser.getId() %>;
        window.isOwnProfile = <%= isOwnProfile %>;
        window.isAdmin = <%= isAdmin %>;
        window.isBlocked = <%= profileUser.isBlocked() %>;
    </script>
    <script src="js/app.js"></script>
</body>
</html>
