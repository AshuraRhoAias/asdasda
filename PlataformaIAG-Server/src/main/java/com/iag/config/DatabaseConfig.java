package com.iag.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    
    private static final String URL = System.getenv("DB_URL") != null
    ? System.getenv("DB_URL")
    : "jdbc:mysql://localhost:3306/bryan?useSSL=false&serverTimezone=UTC";

private static final String USER = System.getenv("DB_USER") != null
    ? System.getenv("DB_USER")
    : "bryan";

private static final String PASSWORD = System.getenv("DB_PASSWORD") != null
    ? System.getenv("DB_PASSWORD")
    : "bryan";
    
    private static DatabaseConfig instance;
    
    private DatabaseConfig() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver MySQL", e);
        }
    }
    
    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
