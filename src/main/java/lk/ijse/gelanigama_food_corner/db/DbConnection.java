package lk.ijse.gelanigama_food_corner.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    
    private final String DB_URL = "jdbc:mysql://localhost:3306/gelanigama_food_corner";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "Root@1234";
    
    private static DbConnection dbc;
    private final Connection connection;
    
    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
    public static DbConnection getInstance()throws SQLException {
        return (dbc==null) ? dbc = new DbConnection() : dbc;
    }
    
    public Connection getConnection() {
        return connection;
    }
}