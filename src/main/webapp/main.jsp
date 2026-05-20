<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String userRole = (String) session.getAttribute("userRole");
    String userEmail = (String) session.getAttribute("userEmail");
    Integer userId = (Integer) session.getAttribute("userId");
    if (userRole == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    boolean isAdmin = "admin".equals(userRole);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Lugus - Principal</title>
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
            <button id="uploadBtn"><img src="images/upload-icon.svg" alt="Subir"> Subir Documento</button>
            <% if (isAdmin) { %>
                <button id="adminBtn" class="admin-btn">
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                </button>
            <% } %>
            <a href="profile" id="profileBtn" class="profile-link">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
            </a>
            <a href="logout" id="logoutBtn"><img src="images/exit-icon.svg" alt="Cerrar sesión"></a>
        </nav>
    </header>

    <div id="uploadModal" class="modal">
        <div class="modal-content">
            <h2>Subir Documento</h2>
            <form id="uploadForm">
                <label for="docTitle">T&iacute;tulo del documento</label>
                <input type="text" id="docTitle" name="title" required>

                <label for="docAuthor">Autor</label>
                <input type="text" id="docAuthor" name="author" required>

                <label for="docInfo">Informaci&oacute;n del documento</label>
                <textarea id="docInfo" name="info" rows="4"></textarea>

                <label for="docFile">Archivo PDF</label>
                <div class="file-drop-zone" id="fileDropZone">
                    <input type="file" id="docFile" name="file" accept=".pdf" required>
                    <div class="drop-content">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="17 8 12 3 7 8"/><line x1="12" y1="3" x2="12" y2="15"/></svg>
                        <span class="drop-text">Haga clic o arrastre el PDF aqu&iacute;</span>
                        <span class="drop-filename" id="fileNameDisplay"></span>
                    </div>
                </div>

                <div class="form-buttons">
                    <button type="button" class="btn-cancel" id="cancelBtn">Cancelar</button>
                    <button type="submit" id="submitUpload">Subir Documento</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Full Document Viewer Modal -->
    <div id="viewerModal" class="modal full-doc-modal">
        <div class="modal-content viewer-content">
            <div class="viewer-top-bar">
                <button id="closeViewerBtn" class="btn-close-modal">&times;</button>
                <a href="#" id="viewerDownloadBtn" class="viewer-download-btn">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg>
                    Descargar PDF
                </a>
            </div>
            <div class="viewer-body">
                <div class="viewer-pdf" id="pdfViewer">
                    <embed id="pdfEmbed" src="" type="application/pdf" width="100%" height="100%">
                </div>
                <div class="viewer-sidebar">
                    <div class="viewer-meta">
                        <h2 id="viewerTitle"></h2>
                        <p class="viewer-author">Por <span id="viewerAuthor"></span></p>
                        <p class="viewer-date">Publicado el <span id="viewerDate"></span></p>
                        <div class="viewer-info" id="viewerInfo"></div>
                    </div>
                    <div class="viewer-uploader" id="viewerUploader"></div>
                    <% if (isAdmin) { %>
                        <div class="viewer-admin-actions">
                            <h3>Administraci&oacute;n</h3>
                            <button id="adminDeleteDocBtn" class="btn-admin-delete">
                                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/></svg>
                                Eliminar documento
                            </button>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <!-- Admin Dashboard Modal -->
    <div id="adminDashboardModal" class="modal">
        <div class="modal-content admin-dashboard">
            <h2>Panel de Administraci&oacute;n</h2>
            <div class="admin-cards">
                <button id="usersCardBtn" class="admin-card">
                    <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                    <span>Usuarios</span>
                </button>
                <button id="docsCardBtn" class="admin-card">
                    <svg width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="8" y1="13" x2="16" y2="13"/><line x1="8" y1="17" x2="13" y2="17"/></svg>
                    <span>Documentos</span>
                </button>
            </div>
        </div>
    </div>

    <!-- Users List Modal -->
    <div id="usersListModal" class="modal">
        <div class="modal-content users-list-content">
            <div class="modal-header">
                <h2>Lista de Usuarios</h2>
                <button id="closeUsersListBtn" class="btn-close-modal">&times;</button>
            </div>
            <div class="users-table-wrapper">
                <table class="users-table">
                    <thead>
                        <tr>
                            <th>Usuario</th>
                            <th>Correo</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="usersTableBody"></tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Document Delete/Block Modal (Admin) -->
    <div id="docBlockModal" class="modal">
        <div class="modal-content block-modal">
            <div class="modal-header">
                <h2>Gestionar documento</h2>
                <button class="btn-close-modal" id="closeDocBlockModal">&times;</button>
            </div>
            <form id="docBlockForm">
                <input type="hidden" id="docBlockDocId" value="">
                <div class="form-group">
                    <label for="docBlockReason">Raz&oacute;n</label>
                    <textarea id="docBlockReason" name="reason" rows="3" placeholder="Indica la razón de esta acción..." required></textarea>
                </div>
                <div class="form-buttons">
                    <button type="button" class="btn-cancel" id="cancelDocBlockBtn">Cancelar</button>
                    <button type="submit" id="confirmDocDelete" class="btn-confirm-delete">Eliminar documento</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Document Info Modal (shown when clicking "Ver") -->
    <div id="docInfoModal" class="modal">
        <div class="modal-content doc-info-content">
            <div class="modal-header">
                <h2 id="docInfoTitle"></h2>
                <button class="btn-close-modal" id="closeDocInfoModal">&times;</button>
            </div>
            <div class="doc-info-body">
                <div class="doc-info-meta-grid">
                    <div class="doc-info-meta-item">
                        <div class="doc-info-meta-icon">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                        </div>
                        <div>
                            <span class="doc-info-label">Autor</span>
                            <span class="doc-info-value" id="docInfoAuthor"></span>
                        </div>
                    </div>
                    <div class="doc-info-meta-item">
                        <div class="doc-info-meta-icon">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/></svg>
                        </div>
                        <div>
                            <span class="doc-info-label">Fecha de subida</span>
                            <span class="doc-info-value" id="docInfoDate"></span>
                        </div>
                    </div>
                    <div class="doc-info-meta-item doc-info-uploader-item">
                        <div class="doc-info-meta-icon">
                            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="17 8 12 3 7 8"/><line x1="12" y1="3" x2="12" y2="15"/></svg>
                        </div>
                        <div>
                            <span class="doc-info-label">Subido por</span>
                            <span id="docInfoUploader"></span>
                        </div>
                    </div>
                </div>
                <div class="doc-info-desc-section">
                    <span class="doc-info-label">Descripci&oacute;n del documento</span>
                    <div class="doc-info-desc-text" id="docInfoDesc"></div>
                </div>
            </div>
            <div class="doc-info-actions">
                <button class="btn-cancel" id="closeDocInfoBtn">Cerrar</button>
                <a href="#" id="docInfoDownloadBtn" class="btn-doc-info-download">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg>
                    Descargar
                </a>
                <button class="btn-doc-info-view" id="docInfoViewPdfBtn">
                    <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                    Ver PDF
                </button>
            </div>
        </div>
    </div>

    <!-- Admin: Bloquear Usuario desde tabla -->
    <div id="adminUserBlockModal" class="modal">
        <div class="modal-content block-modal">
            <div class="modal-header">
                <h2>Bloquear usuario</h2>
                <button class="btn-close-modal" id="closeAdminUserBlockModal">&times;</button>
            </div>
            <form id="adminUserBlockForm">
                <input type="hidden" id="adminBlockUserId" value="">
                <p class="admin-action-username" id="adminBlockUsername"></p>
                <div class="form-group">
                    <label for="adminBlockReason">Raz&oacute;n del bloqueo</label>
                    <textarea id="adminBlockReason" name="reason" rows="3" placeholder="Indica la razón del bloqueo..." required></textarea>
                </div>
                <div class="form-group">
                    <label>Duraci&oacute;n del bloqueo</label>
                    <div class="duration-options">
                        <label class="duration-option">
                            <input type="radio" name="adminBlockDuration" value="1h">
                            <span>1 hora</span>
                        </label>
                        <label class="duration-option">
                            <input type="radio" name="adminBlockDuration" value="1" checked>
                            <span>1 d&iacute;a</span>
                        </label>
                        <label class="duration-option">
                            <input type="radio" name="adminBlockDuration" value="7">
                            <span>7 d&iacute;as</span>
                        </label>
                        <label class="duration-option">
                            <input type="radio" name="adminBlockDuration" value="30">
                            <span>30 d&iacute;as</span>
                        </label>
                        <label class="duration-option">
                            <input type="radio" name="adminBlockDuration" value="permanente">
                            <span>Permanente</span>
                        </label>
                    </div>
                </div>
                <div class="form-buttons">
                    <button type="button" class="btn-cancel" id="cancelAdminUserBlockBtn">Cancelar</button>
                    <button type="submit" class="btn-confirm-block">Bloquear usuario</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Admin: Eliminar Usuario desde tabla -->
    <div id="adminUserDeleteModal" class="modal">
        <div class="modal-content block-modal">
            <div class="modal-header">
                <h2>Eliminar usuario</h2>
                <button class="btn-close-modal" id="closeAdminUserDeleteModal">&times;</button>
            </div>
            <form id="adminUserDeleteForm">
                <input type="hidden" id="adminDeleteUserId" value="">
                <p class="admin-action-username" id="adminDeleteUsername"></p>
                <p class="unblock-info">Esta acci&oacute;n es irreversible. El usuario y todos sus datos ser&aacute;n eliminados permanentemente.</p>
                <div class="form-group">
                    <label for="adminDeleteReason">Motivo de la eliminaci&oacute;n</label>
                    <textarea id="adminDeleteReason" name="reason" rows="3" placeholder="Indica el motivo..." required></textarea>
                </div>
                <div class="form-buttons">
                    <button type="button" class="btn-cancel" id="cancelAdminUserDeleteBtn">Cancelar</button>
                    <button type="submit" class="btn-confirm-delete">Eliminar usuario</button>
                </div>
            </form>
        </div>
    </div>

    <section class="hero">
        <h1>Acceda a nuestra colecci&oacute;n de documentos hist&oacute;ricos pol&eacute;micos</h1>
        <p>Explore archivos y nutrase de informaci&oacute;n nunca antes vista para su investigaci&oacute;n acad&eacute;mica revisionista.</p>
    </section>

    <div class="search-bar">
        <input type="text" id="searchInput" placeholder="Buscar por t&iacute;tulo o autor...">
    </div>

    <section class="document-grid" id="documentGrid"></section>

    <footer class="site-footer">
        <div class="footer-content">
            <p>&copy; 2026 Biblioteca Lugus. Todos los derechos reservados.</p>
            <p class="footer-tagline">Conocimiento sin fronteras, verdad sin censura.</p>
        </div>
    </footer>

    <script>window.isAdmin = <%= isAdmin %>;</script>
    <script src="js/app.js"></script>
</body>
</html>
