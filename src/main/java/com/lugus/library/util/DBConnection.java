package com.lugus.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        String url = null;
        String user = null;
        String pass = null;

        // 1. Try Railway MySQL URL (full connection string)
        String mysqlUrl = System.getenv("MYSQL_URL");
        if (mysqlUrl != null && mysqlUrl.startsWith("mysql://")) {
            // Parse mysql://user:pass@host:port/db
            try {
                String rest = mysqlUrl.substring(8);
                int atIdx = rest.indexOf('@');
                if (atIdx > 0) {
                    String userPass = rest.substring(0, atIdx);
                    int colonIdx = userPass.indexOf(':');
                    user = userPass.substring(0, colonIdx);
                    pass = (colonIdx > 0) ? userPass.substring(colonIdx + 1) : "";
                    String hostPart = rest.substring(atIdx + 1);
                    int slashIdx = hostPart.indexOf('/');
                    String hostPort = (slashIdx > 0) ? hostPart.substring(0, slashIdx) : hostPart;
                    String db = (slashIdx > 0) ? hostPart.substring(slashIdx + 1) : "";
                    if (db.contains("?")) db = db.substring(0, db.indexOf('?'));
                    url = "jdbc:mysql://" + hostPort + "/" + db
                        + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                }
            } catch (Exception e) {
                System.out.println("Failed to parse MYSQL_URL: " + e.getMessage());
            }
        }

        // 2. Try Railway individual env vars (with underscores)
        if (url == null) {
            String host = System.getenv("MYSQL_HOST");
            if (host == null) host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQL_PORT");
            if (port == null) port = System.getenv("MYSQLPORT");
            String db = System.getenv("MYSQL_DATABASE");
            if (db == null) db = System.getenv("MYSQLDATABASE");
            if (host != null && db != null) {
                user = (user == null) ? (System.getenv("MYSQL_USER") != null ? System.getenv("MYSQL_USER") : System.getenv("MYSQLUSER")) : user;
                pass = (pass == null) ? (System.getenv("MYSQL_PASSWORD") != null ? System.getenv("MYSQL_PASSWORD") : System.getenv("MYSQLPASSWORD")) : pass;
                if (port == null) port = "3306";
                url = "jdbc:mysql://" + host + ":" + port + "/" + db
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
                if (user == null) user = "root";
                if (pass == null) pass = "";
            }
        }

        // 3. Fallback to DB_URL / local
        if (url == null) {
            url = System.getenv("DB_URL") != null
                ? System.getenv("DB_URL")
                : "jdbc:mysql://localhost:3306/lugus_library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            user = (user != null) ? user : (System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root");
            pass = (pass != null) ? pass : (System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "root");
        }

        URL = url;
        USER = user;
        PASSWORD = pass;

        System.out.println("DBConnection: URL=" + URL + " USER=" + USER + " PASSWORD=" + (PASSWORD.isEmpty() ? "(empty)" : "***"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
