document.addEventListener('DOMContentLoaded', function () {
    // Password toggle
    document.querySelectorAll('.toggle-password').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var target = document.getElementById(btn.getAttribute('data-target'));
            if (target) {
                if (target.type === 'password') {
                    target.type = 'text';
                    btn.innerHTML = '<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>';
                } else {
                    target.type = 'password';
                    btn.innerHTML = '<svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>';
                }
            }
        });
    });

    var loginTab = document.getElementById('loginTab');
    var registerTab = document.getElementById('registerTab');
    var loginForm = document.getElementById('loginForm');
    var registerForm = document.getElementById('registerForm');

    if (loginTab && registerTab) {
        loginTab.addEventListener('click', function () {
            loginTab.classList.add('active');
            registerTab.classList.remove('active');
            loginForm.classList.add('active');
            registerForm.classList.remove('active');
        });
        registerTab.addEventListener('click', function () {
            registerTab.classList.add('active');
            loginTab.classList.remove('active');
            registerForm.classList.add('active');
            loginForm.classList.remove('active');
        });
    }

    if (loginForm) {
        loginForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var data = new URLSearchParams(new FormData(loginForm));
            fetch('auth', { method: 'POST', body: data, credentials: 'same-origin' })
                .then(function (res) { return res.json(); })
                .then(function (data) {
                    if (data.success) { window.location.href = 'main.jsp'; }
                    else { alert(data.error); }
                })
                .catch(function () { alert('Error al iniciar sesi\u00f3n.'); });
        });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var pwd = document.getElementById('regPassword').value;
            var confirm = document.getElementById('regConfirm').value;
            if (pwd !== confirm) { alert('Las contrase\u00f1as no coinciden'); return; }
            var data = new URLSearchParams(new FormData(registerForm));
            fetch('register', { method: 'POST', body: data, credentials: 'same-origin' })
                .then(function (res) { return res.json(); })
                .then(function (data) {
                    if (data.success) { window.location.href = 'main.jsp'; }
                    else { alert(data.error); }
                })
                .catch(function () { alert('Error al registrarse.'); });
        });
    }

    if (document.getElementById('documentGrid')) {
        loadDocuments();
        setupMainPage();
    }

    if (document.getElementById('profileForm')) {
        setupProfilePage();
    }
});

function loadDocuments() {
    fetch('documents', { credentials: 'same-origin' })
        .then(function (res) { return res.json(); })
        .then(function (docs) { renderDocuments(docs); })
        .catch(function () { var g = document.getElementById('documentGrid'); if (g) g.innerHTML = '<p style="text-align:center;color:var(--text-secondary);padding:2rem;">Error al cargar los documentos.</p>'; });
}

function renderDocuments(docs) {
    var grid = document.getElementById('documentGrid');
    if (!grid) return;
    if (docs.length === 0) {
        grid.innerHTML = '<p style="text-align:center;color:var(--text-secondary);padding:2rem;">A\u00fan no hay documentos.</p>';
        return;
    }
    grid.innerHTML = '';
    docs.forEach(function (doc) {
        var card = document.createElement('div');
        card.className = 'document-card';
        var viewIcon = '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>';
        var downloadIcon = '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" y1="15" x2="12" y2="3"/></svg>';
        var trashIcon = '<svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"/><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/><line x1="10" y1="11" x2="10" y2="17"/><line x1="14" y1="11" x2="14" y2="17"/></svg>';
        var docIcon = '<svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="8" y1="13" x2="16" y2="13"/><line x1="8" y1="17" x2="13" y2="17"/></svg>';
        var actions = '<button class="view-btn icon-text" data-id="' + doc.id + '">' + viewIcon + '<span>Ver</span></button>' +
                      '<button class="download-btn icon-text" data-id="' + doc.id + '">' + downloadIcon + '<span>Descargar</span></button>';
        if (window.isAdmin) {
            actions += '<button class="delete-btn icon-only" data-id="' + doc.id + '">' + trashIcon + '</button>';
        }
        var uploaderHtml = '';
        if (doc.uploadedBy) {
            var name = doc.uploaderName || (doc.uploaderEmail ? doc.uploaderEmail.split('@')[0] : 'Usuario');
            uploaderHtml = '<a href="profile?id=' + doc.uploadedBy + '" class="doc-uploader">Subido por: ' + escapeHtml(name) + '</a>';
        }
        card.innerHTML = '<div class="doc-icon">' + docIcon + '</div>' +
                         '<h3>' + escapeHtml(doc.title) + '</h3>' +
                         '<p class="doc-author">Por ' + escapeHtml(doc.author) + '</p>' +
                         '<p class="doc-date">' + escapeHtml(doc.createdAt) + '</p>' +
                         uploaderHtml +
                         '<div class="doc-actions">' + actions + '</div>';
        grid.appendChild(card);
    });
}

function setupMainPage() {
    var uploadBtn = document.getElementById('uploadBtn');
    var uploadModal = document.getElementById('uploadModal');
    var cancelBtn = document.getElementById('cancelBtn');
    var uploadForm = document.getElementById('uploadForm');
    var viewerModal = document.getElementById('viewerModal');
    var closeViewerBtn = document.getElementById('closeViewerBtn');
    var searchInput = document.getElementById('searchInput');
    var adminBtn = document.getElementById('adminBtn');
    var adminDashboardModal = document.getElementById('adminDashboardModal');
    var usersCardBtn = document.getElementById('usersCardBtn');
    var docsCardBtn = document.getElementById('docsCardBtn');
    var usersListModal = document.getElementById('usersListModal');
    var closeUsersListBtn = document.getElementById('closeUsersListBtn');
    var docBlockModal = document.getElementById('docBlockModal');
    var closeDocBlockBtn = document.getElementById('closeDocBlockModal');
    var cancelDocBlockBtn = document.getElementById('cancelDocBlockBtn');
    var docBlockForm = document.getElementById('docBlockForm');

    if (uploadBtn && uploadModal) {
        uploadBtn.addEventListener('click', function () { uploadModal.classList.add('active'); });
    }
    if (cancelBtn && uploadModal) {
        cancelBtn.addEventListener('click', function () { uploadModal.classList.remove('active'); });
    }
    if (uploadModal) {
        uploadModal.addEventListener('click', function (e) { if (e.target === uploadModal) uploadModal.classList.remove('active'); });
    }

    if (uploadForm) {
        uploadForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var formData = new FormData(uploadForm);
            var btn = document.getElementById('submitUpload');
            btn.disabled = true; btn.textContent = 'Subiendo...';
            fetch('upload', { method: 'POST', body: formData, credentials: 'same-origin' })
                .then(function (res) { return res.json(); })
                .then(function (data) {
                    if (data.success) {
                        uploadModal.classList.remove('active');
                        uploadForm.reset();
                        document.getElementById('fileNameDisplay').classList.remove('visible');
                        document.querySelector('.drop-text').textContent = 'Haga clic o arrastre el PDF aqu\u00ed';
                        loadDocuments();
                    } else if (data.blocked) {
                        alert(data.error);
                    } else { alert(data.error); }
                })
                .catch(function () { alert('Error al subir.'); })
                .finally(function () { btn.disabled = false; btn.textContent = 'Subir Documento'; });
        });
    }

    var docFile = document.getElementById('docFile');
    if (docFile) {
        docFile.addEventListener('change', function () {
            var display = document.getElementById('fileNameDisplay');
            var dropText = document.querySelector('.drop-text');
            if (docFile.files.length > 0) {
                display.textContent = docFile.files[0].name;
                display.classList.add('visible');
                dropText.textContent = 'Archivo seleccionado';
            } else {
                display.classList.remove('visible');
                dropText.textContent = 'Haga clic o arrastre el PDF aqu\u00ed';
            }
        });
    }

    if (closeViewerBtn && viewerModal) {
        closeViewerBtn.addEventListener('click', function () {
            viewerModal.classList.remove('active');
            document.getElementById('pdfEmbed').src = '';
        });
    }
    if (viewerModal) {
        viewerModal.addEventListener('click', function (e) {
            if (e.target === viewerModal) {
                viewerModal.classList.remove('active');
                document.getElementById('pdfEmbed').src = '';
            }
        });
    }

    if (adminBtn && adminDashboardModal) {
        adminBtn.addEventListener('click', function () { adminDashboardModal.classList.add('active'); });
    }
    if (adminDashboardModal) {
        adminDashboardModal.addEventListener('click', function (e) { if (e.target === adminDashboardModal) adminDashboardModal.classList.remove('active'); });
    }
    if (usersCardBtn) {
        usersCardBtn.addEventListener('click', function () {
            adminDashboardModal.classList.remove('active');
            usersListModal.classList.add('active');
            loadUsers();
        });
    }
    if (docsCardBtn) {
        docsCardBtn.addEventListener('click', function () { adminDashboardModal.classList.remove('active'); });
    }
    if (closeUsersListBtn && usersListModal) {
        closeUsersListBtn.addEventListener('click', function () { usersListModal.classList.remove('active'); });
    }
    if (usersListModal) {
        usersListModal.addEventListener('click', function (e) { if (e.target === usersListModal) usersListModal.classList.remove('active'); });
    }

    if (closeDocBlockBtn && docBlockModal) {
        closeDocBlockBtn.addEventListener('click', function () { docBlockModal.classList.remove('active'); });
    }
    if (cancelDocBlockBtn && docBlockModal) {
        cancelDocBlockBtn.addEventListener('click', function () { docBlockModal.classList.remove('active'); });
    }
    if (docBlockModal) {
        docBlockModal.addEventListener('click', function (e) { if (e.target === docBlockModal) docBlockModal.classList.remove('active'); });
    }
    if (docBlockForm) {
        docBlockForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var id = document.getElementById('docBlockDocId').value;
            var reason = document.getElementById('docBlockReason').value;
            document.getElementById('confirmDocDelete').textContent = 'Eliminando...';
            document.getElementById('confirmDocDelete').disabled = true;
            fetch('delete', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'id=' + id + '&reason=' + encodeURIComponent(reason),
                credentials: 'same-origin'
            })
                .then(function (res) { return res.json(); })
                .then(function (data) {
                    if (data.success) {
                        docBlockModal.classList.remove('active');
                        viewerModal.classList.remove('active');
                        document.getElementById('pdfEmbed').src = '';
                        loadDocuments();
                    } else { alert(data.error); }
                })
                .catch(function () { alert('Error al eliminar.'); })
                .finally(function () {
                    document.getElementById('confirmDocDelete').textContent = 'Eliminar documento';
                    document.getElementById('confirmDocDelete').disabled = false;
                });
        });
    }

    document.addEventListener('click', function (e) {
        var viewBtn = e.target.closest('.view-btn');
        var downloadBtn = e.target.closest('.download-btn');
        var deleteBtn = e.target.closest('.delete-btn');

        if (viewBtn) {
            var id = viewBtn.getAttribute('data-id');
            openDocInfo(parseInt(id));
        }
        if (downloadBtn) {
            window.location.href = 'download?id=' + downloadBtn.getAttribute('data-id');
        }
        if (deleteBtn && window.isAdmin) {
            var id = deleteBtn.getAttribute('data-id');
            document.getElementById('docBlockDocId').value = id;
            document.getElementById('docBlockReason').value = '';
            document.getElementById('docBlockModal').classList.add('active');
        }
    });

    // --- Doc Info Modal ---
    var docInfoModal = document.getElementById('docInfoModal');
    var closeDocInfoModal = document.getElementById('closeDocInfoModal');
    var closeDocInfoBtn = document.getElementById('closeDocInfoBtn');
    if (closeDocInfoModal) closeDocInfoModal.addEventListener('click', function () { docInfoModal.classList.remove('active'); });
    if (closeDocInfoBtn) closeDocInfoBtn.addEventListener('click', function () { docInfoModal.classList.remove('active'); });
    if (docInfoModal) docInfoModal.addEventListener('click', function (e) { if (e.target === docInfoModal) docInfoModal.classList.remove('active'); });

    var docInfoViewPdfBtn = document.getElementById('docInfoViewPdfBtn');
    if (docInfoViewPdfBtn) {
        docInfoViewPdfBtn.addEventListener('click', function () {
            var id = docInfoViewPdfBtn.getAttribute('data-id');
            if (id) {
                docInfoModal.classList.remove('active');
                openViewer(parseInt(id));
            }
        });
    }

    // --- Admin User Block Modal (from table) ---
    var adminUserBlockModal = document.getElementById('adminUserBlockModal');
    var closeAdminUserBlockModal = document.getElementById('closeAdminUserBlockModal');
    var cancelAdminUserBlockBtn = document.getElementById('cancelAdminUserBlockBtn');
    var adminUserBlockForm = document.getElementById('adminUserBlockForm');
    if (closeAdminUserBlockModal) closeAdminUserBlockModal.addEventListener('click', function () { adminUserBlockModal.classList.remove('active'); });
    if (cancelAdminUserBlockBtn) cancelAdminUserBlockBtn.addEventListener('click', function () { adminUserBlockModal.classList.remove('active'); });
    if (adminUserBlockModal) adminUserBlockModal.addEventListener('click', function (e) { if (e.target === adminUserBlockModal) adminUserBlockModal.classList.remove('active'); });
    if (adminUserBlockForm) {
        adminUserBlockForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var uid = parseInt(document.getElementById('adminBlockUserId').value);
            var reason = document.getElementById('adminBlockReason').value;
            var dur = document.querySelector('input[name="adminBlockDuration"]:checked');
            var duration = dur ? dur.value : '1';
            adminUserBlockModal.classList.remove('active');
            blockUser(uid, 'block', reason, duration);
        });
    }

    // --- Admin User Delete Modal (from table) ---
    var adminUserDeleteModal = document.getElementById('adminUserDeleteModal');
    var closeAdminUserDeleteModal = document.getElementById('closeAdminUserDeleteModal');
    var cancelAdminUserDeleteBtn = document.getElementById('cancelAdminUserDeleteBtn');
    var adminUserDeleteForm = document.getElementById('adminUserDeleteForm');
    if (closeAdminUserDeleteModal) closeAdminUserDeleteModal.addEventListener('click', function () { adminUserDeleteModal.classList.remove('active'); });
    if (cancelAdminUserDeleteBtn) cancelAdminUserDeleteBtn.addEventListener('click', function () { adminUserDeleteModal.classList.remove('active'); });
    if (adminUserDeleteModal) adminUserDeleteModal.addEventListener('click', function (e) { if (e.target === adminUserDeleteModal) adminUserDeleteModal.classList.remove('active'); });
    if (adminUserDeleteForm) {
        adminUserDeleteForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var uid = parseInt(document.getElementById('adminDeleteUserId').value);
            var reason = document.getElementById('adminDeleteReason').value;
            adminUserDeleteModal.classList.remove('active');
            deleteUser(uid, reason);
        });
    }

    if (searchInput) {
        var timeout;
        searchInput.addEventListener('input', function () {
            clearTimeout(timeout);
            timeout = setTimeout(function () {
                var q = searchInput.value.trim();
                if (q === '') loadDocuments();
                else searchDocuments(q);
            }, 300);
        });
    }
}

function openDocInfo(id) {
    fetch('document?id=' + id, { credentials: 'same-origin' })
        .then(function (res) { return res.json(); })
        .then(function (doc) {
            document.getElementById('docInfoTitle').textContent = doc.title || '';
            document.getElementById('docInfoAuthor').textContent = doc.author || '-';
            document.getElementById('docInfoDate').textContent = doc.createdAt || '-';
            document.getElementById('docInfoDesc').textContent = doc.info && doc.info.trim() ? doc.info : 'Sin descripci\u00f3n disponible.';
            document.getElementById('docInfoDownloadBtn').href = 'download?id=' + id;
            var viewBtn = document.getElementById('docInfoViewPdfBtn');
            if (viewBtn) viewBtn.setAttribute('data-id', id);
            var uploaderSpan = document.getElementById('docInfoUploader');
            if (doc.uploadedBy) {
                var name = doc.uploaderName || (doc.uploaderEmail ? doc.uploaderEmail.split('@')[0] : 'Usuario');
                uploaderSpan.innerHTML = '<a href="profile?id=' + doc.uploadedBy + '" class="uploader-link"><svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg> ' + escapeHtml(name) + '</a>';
            } else {
                uploaderSpan.textContent = 'Desconocido';
            }
            document.getElementById('docInfoModal').classList.add('active');
        })
        .catch(function (e) { console.error('Error al cargar documento:', e); alert('Error al cargar los detalles del documento.'); });
}

function openViewer(id) {
    fetch('document?id=' + id, { credentials: 'same-origin' })
        .then(function (res) { return res.json(); })
        .then(function (doc) {
            document.getElementById('viewerTitle').textContent = doc.title;
            document.getElementById('viewerAuthor').textContent = doc.author;
            document.getElementById('viewerDate').textContent = doc.createdAt;
            document.getElementById('viewerInfo').textContent = doc.info || 'Sin informaci\u00f3n adicional.';
            document.getElementById('pdfEmbed').src = 'download?id=' + id + '&view=true#view=FitH';
            document.getElementById('viewerDownloadBtn').href = 'download?id=' + id;
            var uploaderDiv = document.getElementById('viewerUploader');
            if (doc.uploadedBy) {
                var name = doc.uploaderName || (doc.uploaderEmail ? doc.uploaderEmail.split('@')[0] : 'Usuario');
                uploaderDiv.innerHTML = '<div class="uploader-section"><h3>Subido por</h3><a href="profile?id=' + doc.uploadedBy + '" class="uploader-link"><svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg> ' + escapeHtml(name) + '</a></div>';
            }
            var deleteBtn = document.getElementById('adminDeleteDocBtn');
            if (deleteBtn) {
                deleteBtn.onclick = function () {
                    document.getElementById('docBlockDocId').value = doc.id;
                    document.getElementById('docBlockReason').value = '';
                    document.getElementById('docBlockModal').classList.add('active');
                };
            }
            document.getElementById('viewerModal').classList.add('active');
        })
        .catch(function (e) { console.error('Error al cargar documento:', e); alert('Error al cargar el documento.'); });
}

function searchDocuments(q) {
    fetch('search?q=' + encodeURIComponent(q), { credentials: 'same-origin' })
        .then(function (res) { return res.json(); })
        .then(function (docs) { renderDocuments(docs); })
        .catch(function () { alert('Error en la b\u00fasqueda.'); });
}

function loadUsers() {
    var tbody = document.getElementById('usersTableBody');
    if (!tbody) return;
    tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">Cargando...</td></tr>';
    fetch('users', { credentials: 'same-origin' })
        .then(function (res) { return res.json(); })
        .then(function (users) { renderUsersTable(users); })
        .catch(function () { tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;color:var(--accent-red);">Error al cargar usuarios.</td></tr>'; });
}

function renderUsersTable(users) {
    var tbody = document.getElementById('usersTableBody');
    if (!tbody) return;
    if (users.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" style="text-align:center;">No hay usuarios.</td></tr>';
        return;
    }
    tbody.innerHTML = '';
    users.forEach(function (u) {
        var row = document.createElement('tr');
        var statusHtml = u.blocked ? '<span class="status-blocked">Bloqueado</span>' : '<span class="status-active">Activo</span>';
        var actionsHtml = '';
        var displayName = escapeHtml(u.username || u.email.split('@')[0]);
        if (u.role !== 'admin') {
            actionsHtml = '<button class="btn-table-block" data-id="' + u.id + '" data-name="' + displayName + '" data-action="' + (u.blocked ? 'unblock' : 'block') + '">' + (u.blocked ? 'Desbloquear' : 'Bloquear') + '</button>' +
                          '<button class="btn-table-delete" data-id="' + u.id + '" data-name="' + displayName + '">Eliminar</button>';
        } else {
            actionsHtml = '<span class="no-action">-</span>';
        }
        row.innerHTML = '<td>' + displayName + '</td>' +
                        '<td>' + escapeHtml(u.email) + '</td>' +
                        '<td>' + (u.role === 'admin' ? 'Admin' : 'Usuario') + '</td>' +
                        '<td>' + statusHtml + '</td>' +
                        '<td class="table-actions">' + actionsHtml + '</td>';
        tbody.appendChild(row);
    });

    tbody.querySelectorAll('.btn-table-block').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var uid = parseInt(btn.getAttribute('data-id'));
            var action = btn.getAttribute('data-action');
            var name = btn.getAttribute('data-name') || 'este usuario';
            if (action === 'unblock') {
                if (confirm('\u00bfDeseas desbloquear a ' + name + '?')) {
                    blockUser(uid, 'unblock', '', '');
                }
            } else {
                var modal = document.getElementById('adminUserBlockModal');
                if (modal) {
                    document.getElementById('adminBlockUserId').value = uid;
                    document.getElementById('adminBlockUsername').textContent = 'Usuario: ' + name;
                    document.getElementById('adminBlockReason').value = '';
                    var defaultDur = document.querySelector('input[name="adminBlockDuration"][value="1"]');
                    if (defaultDur) defaultDur.checked = true;
                    modal.classList.add('active');
                }
            }
        });
    });

    tbody.querySelectorAll('.btn-table-delete').forEach(function (btn) {
        btn.addEventListener('click', function () {
            var uid = parseInt(btn.getAttribute('data-id'));
            var name = btn.getAttribute('data-name') || 'este usuario';
            var modal = document.getElementById('adminUserDeleteModal');
            if (modal) {
                document.getElementById('adminDeleteUserId').value = uid;
                document.getElementById('adminDeleteUsername').textContent = 'Usuario: ' + name;
                document.getElementById('adminDeleteReason').value = '';
                modal.classList.add('active');
            }
        });
    });
}

function blockUser(userId, action, reason, duration) {
    console.log('blockUser called:', { userId: userId, action: action, reason: reason, duration: duration });
    var body = 'id=' + userId + '&action=' + action;
    if (action === 'block') {
        body += '&reason=' + encodeURIComponent(reason);
        body += '&until=' + encodeURIComponent(duration);
    }
    fetch('block', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: body, credentials: 'same-origin'
    })
        .then(function (res) { return res.json(); })
        .then(function (data) {
            console.log('blockUser response:', data);
            if (data.success) {
                alert(action === 'block' ? 'Usuario bloqueado' : 'Usuario desbloqueado');
                loadUsers();
                if (window.profileUserId) window.location.reload();
            } else {
                console.error('blockUser error response:', data);
                alert(data.error || 'Error.');
            }
        })
        .catch(function (err) {
            console.error('blockUser fetch error:', err);
            alert('Error.');
        });
}

function deleteUser(userId, reason) {
    fetch('delete-user', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'id=' + userId + '&reason=' + encodeURIComponent(reason || ''),
        credentials: 'same-origin'
    })
        .then(function (res) { return res.json(); })
        .then(function (data) {
            if (data.success) {
                alert('Usuario eliminado');
                loadUsers();
                if (window.profileUserId) window.location.href = 'main.jsp';
            } else { alert(data.error || 'Error.'); }
        })
        .catch(function () { alert('Error.'); });
}

function setupProfilePage() {
    var profileForm = document.getElementById('profileForm');
    var blockModal = document.getElementById('blockModal');
    var blockForm = document.getElementById('blockForm');
    var unblockForm = document.getElementById('unblockForm');
    var blockUserBtn = document.getElementById('blockUserBtn');
    var deleteUserBtn = document.getElementById('deleteUserBtn');
    var cancelBlockBtn = document.getElementById('cancelBlockBtn');
    var cancelUnblockBtn = document.getElementById('cancelUnblockBtn');
    var closeBlockModal = document.getElementById('closeBlockModal');

    if (profileForm) {
        profileForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var data = new URLSearchParams(new FormData(profileForm));
            fetch('profile', { method: 'POST', body: data, credentials: 'same-origin' })
                .then(function (res) { return res.json(); })
                .then(function (d) {
                    if (d.success) { alert('Perfil actualizado'); window.location.reload(); }
                    else { alert(d.error); }
                })
                .catch(function () { alert('Error.'); });
        });
    }

    if (blockUserBtn && blockModal) {
        blockUserBtn.addEventListener('click', function () { blockModal.classList.add('active'); });
    }
    if (cancelBlockBtn && blockModal) {
        cancelBlockBtn.addEventListener('click', function () { blockModal.classList.remove('active'); });
    }
    if (cancelUnblockBtn && blockModal) {
        cancelUnblockBtn.addEventListener('click', function () { blockModal.classList.remove('active'); });
    }
    if (closeBlockModal && blockModal) {
        closeBlockModal.addEventListener('click', function () { blockModal.classList.remove('active'); });
    }
    if (blockModal) {
        blockModal.addEventListener('click', function (e) { if (e.target === blockModal) blockModal.classList.remove('active'); });
    }

    if (blockForm) {
        blockForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var uid = document.getElementById('blockUserId').value;
            var reason = document.getElementById('blockReason').value;
            var duration = document.querySelector('input[name="duration"]:checked');
            var durVal = duration ? duration.value : '7';
            if (window.isBlocked) {
                blockUser(parseInt(uid), 'unblock', '', '');
            } else {
                blockUser(parseInt(uid), 'block', reason, durVal);
            }
            blockModal.classList.remove('active');
        });
    }

    if (unblockForm) {
        unblockForm.addEventListener('submit', function (e) {
            e.preventDefault();
            var uid = document.getElementById('unblockUserId').value;
            blockUser(parseInt(uid), 'unblock', '', '');
            blockModal.classList.remove('active');
        });
    }

    if (deleteUserBtn) {
        deleteUserBtn.addEventListener('click', function () {
            var reason = prompt('Motivo de la eliminaci\u00f3n:');
            if (reason === null || reason.trim() === '') { alert('Debes escribir una raz\u00f3n.'); return; }
            if (confirm('\u00bfEst\u00e1 seguro de eliminar este usuario?')) {
                deleteUser(window.profileUserId, reason);
            }
        });
    }
}

function escapeHtml(str) {
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(str));
    return div.innerHTML;
}
