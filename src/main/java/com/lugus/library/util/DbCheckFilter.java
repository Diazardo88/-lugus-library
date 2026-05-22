package com.lugus.library.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Statement;

@WebFilter("/*")
public class DbCheckFilter implements Filter {

    private static boolean initialized = false;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws java.io.IOException, ServletException {
        if (!initialized) {
            synchronized (DbCheckFilter.class) {
                if (!initialized) {
                    // Log env vars for debugging
                    System.out.println("=== Railway Env Vars ===");
                    String[] vars = {"MYSQL_HOST","MYSQL_PORT","MYSQL_DATABASE","MYSQL_USER","MYSQL_PASSWORD",
                        "MYSQLHOST","MYSQLPORT","MYSQLDATABASE","MYSQLUSER","MYSQLPASSWORD",
                        "MYSQL_URL","DB_URL","DB_USER","DB_PASSWORD","UPLOAD_DIR"};
                    for (String v : vars) {
                        String val = System.getenv(v);
                        if (val != null) {
                            String display = v.contains("PASSWORD") ? "***" : val;
                            System.out.println("  " + v + " = " + display);
                        }
                    }
                    System.out.println("========================");

                    try (Connection conn = DBConnection.getConnection();
                         Statement st = conn.createStatement()) {

                        st.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                            + "id INT AUTO_INCREMENT PRIMARY KEY,"
                            + "email VARCHAR(255) NOT NULL UNIQUE,"
                            + "username VARCHAR(255) DEFAULT NULL,"
                            + "password VARCHAR(64) NOT NULL,"
                            + "role VARCHAR(10) NOT NULL DEFAULT 'user',"
                            + "avatar VARCHAR(500) DEFAULT NULL,"
                            + "banner VARCHAR(500) DEFAULT NULL,"
                            + "blocked TINYINT(1) DEFAULT 0,"
                            + "block_reason VARCHAR(500) DEFAULT NULL,"
                            + "block_until VARCHAR(50) DEFAULT NULL,"
                            + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                            + ")");

                        st.executeUpdate("CREATE TABLE IF NOT EXISTS documents ("
                            + "id INT AUTO_INCREMENT PRIMARY KEY,"
                            + "title VARCHAR(500) NOT NULL,"
                            + "author VARCHAR(255) NOT NULL,"
                            + "info TEXT,"
                            + "filename VARCHAR(500) NOT NULL,"
                            + "uploaded_by INT NOT NULL,"
                            + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                            + "FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE CASCADE"
                            + ")");

                        st.executeUpdate("INSERT IGNORE INTO users (email, password, role) "
                            + "VALUES ('admin@lugus.com', SHA2('admin123', 256), 'admin')");
                        st.executeUpdate("INSERT IGNORE INTO users (email, password, role) "
                            + "VALUES ('rodri.diazramos99@gmail.com', SHA2('admin123', 256), 'admin')");

                        initialized = true;
                        System.out.println("DB check filter: tables ready");
                    } catch (Exception e) {
                        System.out.println("DB check filter failed: " + e.getMessage());
                    }
                }
            }
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig fc) {}
    public void destroy() {}
}
