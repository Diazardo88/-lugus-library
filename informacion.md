# Lugus Library - Project Information

## Figma URL
[Figma Design System & Wireframes](https://www.figma.com/design/YOUR_DESIGN_ID_HERE)

## Project Overview
Lugus Library is a web platform built with Jakarta EE (MVC pattern) that allows users to access and manage a collection of controversial historical documents. The application supports two user roles: admin and normal user, with appropriate permissions for each.

## Technologies Used
- **Backend:** Jakarta EE 10 (Servlets), Java 17
- **Frontend:** HTML5, CSS3 (Flexbox/Grid), JavaScript (Promises, Fetch API)
- **Database:** MySQL 8
- **Deployment:** Docker, Docker Compose, Railway

## Design System
- **Color Palette:** Dark theme (#0a0e17, #111827, #1a2332) with gold accents (#c9a84c) and red for destructive actions (#dc2626)
- **Typography:** Uncial Antiqua (Celtic font for headings), Segoe UI (body text)
- **Iconography:** Custom image icons for document, view, download, trash, exit, and upload actions
- **States:** Hover, focus, active, and error states defined throughout the interface

## Architecture (MVC)
- **Model:** User.java, Document.java, UserDAO.java, DocumentDAO.java
- **View:** index.jsp, main.jsp, style.css
- **Controller:** AuthServlet, RegisterServlet, LogoutServlet, UploadServlet, ListDocumentsServlet, ViewDocumentServlet, DeleteDocumentServlet, SearchServlet, DownloadDocumentServlet

## Default Admin Credentials
- **Email:** admin@lugus.com
- **Password:** admin123
