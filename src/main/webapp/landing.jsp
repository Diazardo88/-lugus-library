<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Biblioteca Lugus - Inicio</title>
    <link href="https://fonts.googleapis.com/css2?family=Uncial+Antiqua&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="landing-page">
    <div class="landing-bg"></div>

    <header class="landing-header">
        <div class="logo">
            <img src="images/logo-icon.svg" alt="Logo" class="logo-icon">
            <span>Biblioteca Lugus</span>
        </div>
        <nav>
            <a href="index.jsp" class="btn-landing-login">Acceder</a>
        </nav>
    </header>

    <section class="landing-hero">
        <!-- Diseño del dios Lugus fondo -->
        <div class="hero-lugus-bg" aria-hidden="true">
            <img src="images/lugus-god.jpg" alt="Dios Lugus" class="lugus-img">
        </div>
        <!-- Partículas decorativas de fondo -->
        <div class="hero-particles" aria-hidden="true">
            <svg class="hero-particle-svg" viewBox="0 0 900 500" xmlns="http://www.w3.org/2000/svg">
                <circle cx="80" cy="120" r="1.5" fill="rgba(35,134,54,0.4)" class="particle p1"/>
                <circle cx="200" cy="60" r="1" fill="rgba(35,134,54,0.3)" class="particle p2"/>
                <circle cx="820" cy="80" r="2" fill="rgba(35,134,54,0.35)" class="particle p3"/>
                <circle cx="750" cy="200" r="1.2" fill="rgba(35,134,54,0.25)" class="particle p4"/>
                <circle cx="140" cy="380" r="1.8" fill="rgba(35,134,54,0.3)" class="particle p5"/>
                <circle cx="860" cy="350" r="1.3" fill="rgba(35,134,54,0.4)" class="particle p6"/>
                <circle cx="450" cy="30" r="1" fill="rgba(35,134,54,0.2)" class="particle p1"/>
                <circle cx="600" cy="420" r="1.5" fill="rgba(35,134,54,0.3)" class="particle p3"/>
                <circle cx="320" cy="450" r="1" fill="rgba(35,134,54,0.25)" class="particle p2"/>
                <line x1="80" y1="120" x2="200" y2="60" stroke="rgba(35,134,54,0.06)" stroke-width="1"/>
                <line x1="820" y1="80" x2="750" y2="200" stroke="rgba(35,134,54,0.06)" stroke-width="1"/>
                <line x1="140" y1="380" x2="320" y2="450" stroke="rgba(35,134,54,0.06)" stroke-width="1"/>
            </svg>
        </div>

        <div class="hero-content">
            <!-- Badge superior -->
            <div class="hero-badge">
                <span class="hero-badge-dot"></span>
                Archivo Digital Abierto
            </div>

            <!-- Logo e icono -->
            <div class="hero-logo">
                <div class="hero-logo-ring">
                    <img src="images/logo-icon.svg" alt="Logo Biblioteca Lugus" class="hero-logo-icon">
                </div>
            </div>

            <!-- Título principal -->
            <h1 class="hero-title">
                <span class="hero-title-line1">Biblioteca</span>
                <span class="hero-title-line2">Lugus</span>
            </h1>

            <!-- Tagline -->
            <p class="hero-tagline">
                Un archivo digital dedicado a <em>preservar</em> y proporcionar acceso a
                <em>documentos históricos controversiales</em> que moldean nuestra comprensión del pasado.
            </p>

            <!-- Separador decorativo -->
            <div class="hero-separator">
                <span class="hero-sep-line"></span>
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="var(--accent-green)" stroke-width="1.5" opacity="0.6"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                <span class="hero-sep-line"></span>
            </div>

            <!-- Estadísticas -->
            <div class="hero-stats">
                <div class="hero-stat">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
                    <span class="hero-stat-label">Documentos históricos</span>
                </div>
                <div class="hero-stat-divider"></div>
                <div class="hero-stat">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>
                    <span class="hero-stat-label">Acceso libre y abierto</span>
                </div>
                <div class="hero-stat-divider"></div>
                <div class="hero-stat">
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                    <span class="hero-stat-label">Preservación permanente</span>
                </div>
            </div>

            <!-- Botón CTA principal -->
            <a href="index.jsp" class="hero-cta-btn">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/><polyline points="10 17 15 12 10 7"/><line x1="15" y1="12" x2="3" y2="12"/></svg>
                Acceder al archivo
            </a>
        </div>

        <!-- Ola decorativa inferior -->
        <div class="hero-wave" aria-hidden="true">
            <svg viewBox="0 0 1440 60" fill="none" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="none">
                <path d="M0,30 C240,60 480,0 720,30 C960,60 1200,0 1440,30 L1440,60 L0,60 Z" fill="rgba(17,26,17,0.5)"/>
            </svg>
        </div>
    </section>

    <section class="landing-spirit">
        <div class="spirit-divider">
            <svg width="120" height="20" viewBox="0 0 120 20" fill="none" stroke="var(--accent-green)" stroke-width="1.5" opacity="0.5"><path d="M10 10 L30 10 L40 3 L50 17 L60 10 L70 3 L80 17 L90 10 L110 10"/></svg>
        </div>
        <div class="spirit-header">
            <h2 class="section-title">Nuestro Esp&iacute;ritu</h2>
            <p class="section-subtitle">Creemos en el poder del conocimiento libre y sin censura</p>
        </div>
        <div class="spirit-cards">
            <div class="spirit-card">
                <div class="spirit-card-border"></div>
                <div class="spirit-card-glow"></div>
                <div class="spirit-icon">
                    <svg width="56" height="56" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.2" stroke-linecap="round" stroke-linejoin="round"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="8" y1="13" x2="16" y2="13"/><line x1="8" y1="17" x2="13" y2="17"/></svg>
                </div>
                <h3>Nuestra Misi&oacute;n</h3>
                <p>Creemos en la importancia de la transparencia acad&eacute;mica y el derecho fundamental a acceder a informaci&oacute;n que ha sido marginada, censurada u ocultada a lo largo de la historia.</p>
                <div class="spirit-card-footer"><svg width="60" height="8" viewBox="0 0 60 8" fill="none" stroke="var(--accent-green)" stroke-width="0.8" opacity="0.3"><path d="M5 4 L15 4 L20 1 L25 7 L30 4 L35 1 L40 7 L45 4 L55 4"/></svg></div>
            </div>
            <div class="spirit-card">
                <div class="spirit-card-border"></div>
                <div class="spirit-card-glow"></div>
                <div class="spirit-icon">
                    <svg width="56" height="56" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><path d="M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>
                </div>
                <h3>Transparencia</h3>
                <p>Empoderamos a investigadores, historiadores y ciudadanos curiosos con recursos que desaf&iacute;an las narrativas convencionales y revelan verdades inc&oacute;modas.</p>
                <div class="spirit-card-footer"><svg width="60" height="8" viewBox="0 0 60 8" fill="none" stroke="var(--accent-green)" stroke-width="0.8" opacity="0.3"><path d="M5 4 L15 4 L20 1 L25 7 L30 4 L35 1 L40 7 L45 4 L55 4"/></svg></div>
            </div>
            <div class="spirit-card">
                <div class="spirit-card-border"></div>
                <div class="spirit-card-glow"></div>
                <div class="spirit-icon">
                    <svg width="56" height="56" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                </div>
                <h3>Preservaci&oacute;n</h3>
                <p>Preservamos documentos que de otro modo podr&iacute;an perderse en el olvido, garantizando que las generaciones futuras puedan aprender de la historia completa.</p>
                <div class="spirit-card-footer"><svg width="60" height="8" viewBox="0 0 60 8" fill="none" stroke="var(--accent-green)" stroke-width="0.8" opacity="0.3"><path d="M5 4 L15 4 L20 1 L25 7 L30 4 L35 1 L40 7 L45 4 L55 4"/></svg></div>
            </div>
        </div>
        <div class="spirit-divider">
            <svg width="120" height="20" viewBox="0 0 120 20" fill="none" stroke="var(--accent-green)" stroke-width="1.5" opacity="0.5"><path d="M10 10 L30 10 L40 3 L50 17 L60 10 L70 3 L80 17 L90 10 L110 10"/></svg>
        </div>
    </section>

    <section class="landing-cta">
        <div class="cta-content">
            <h2>&Uacute;nete a nuestra comunidad</h2>
            <p>Accede a cientos de documentos exclusivos y contribuye con tus propios hallazgos.</p>
            <a href="index.jsp" class="btn-landing-cta">
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/><polyline points="10 17 15 12 10 7"/><line x1="15" y1="12" x2="3" y2="12"/></svg>
                Comenzar ahora
            </a>
        </div>
    </section>

    <footer class="site-footer">
        <div class="footer-content">
            <p>&copy; 2026 Biblioteca Lugus. Todos los derechos reservados.</p>
            <p class="footer-tagline">Conocimiento sin fronteras, verdad sin censura.</p>
        </div>
    </footer>
</body>
</html>
