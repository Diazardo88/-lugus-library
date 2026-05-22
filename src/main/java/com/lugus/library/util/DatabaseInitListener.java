package com.lugus.library.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class DatabaseInitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        for (int i = 0; i < 10; i++) {
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

                System.out.println("Database initialized successfully");
                return;

            } catch (Exception e) {
                System.out.println("DB init attempt " + (i + 1) + " failed: " + e.getMessage());
                try { Thread.sleep(3000); } catch (InterruptedException ie) {}
            }
        }
        System.err.println("Failed to initialize database after 10 attempts");
    }

    public void contextDestroyed(ServletContextEvent sce) {}
}
