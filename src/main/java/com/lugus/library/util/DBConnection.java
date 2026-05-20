package com.lugus.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // Railway MySQL env vars
        String railwayHost = System.getenv("MYSQL_HOST");
        String railwayPort = System.getenv("MYSQL_PORT");
        String railwayDb = System.getenv("MYSQL_DATABASE");
        String railwayUser = System.getenv("MYSQL_USER");
        String railwayPass = System.getenv("MYSQL_PASSWORD");

        if (railwayHost != null && railwayDb != null) {
            String port = (railwayPort != null) ? railwayPort : "3306";
            URL = "jdbc:mysql://" + railwayHost + ":" + port + "/" + railwayDb
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            USER = (railwayUser != null) ? railwayUser : "root";
            PASSWORD = (railwayPass != null) ? railwayPass : "";
        } else {
            URL = System.getenv("DB_URL") != null
                ? System.getenv("DB_URL")
                : "jdbc:mysql://localhost:3306/lugus_library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "root";
            PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "root";
        }

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
