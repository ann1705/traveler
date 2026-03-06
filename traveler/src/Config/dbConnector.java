package config;

import java.sql.*;

public class dbConnector {
    private static dbConnector instance;
    private Connection connect;

    private dbConnector() {
        try {
            // Ensure the driver is loaded
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:travelDB.db");
            
            // Enable WAL mode to prevent "Database Busy"
            try (Statement stmt = connect.createStatement()) {
                stmt.execute("PRAGMA journal_mode=WAL;");
            }
        } catch (Exception ex) {
            System.out.println("Connection Error: " + ex.getMessage());
        }
    }

    public static synchronized dbConnector getInstance() {
        if (instance == null) {
            instance = new dbConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connect;
    }

   public ResultSet getData(String query) throws SQLException {
    Statement str = connect.createStatement();
    ResultSet rs = str.executeQuery(query);
    return rs; // Do NOT close 'str' here or 'rs' will be empty when it reaches your table!
}
    
   public int insertData(String sql) {
    // Add Statement.RETURN_GENERATED_KEYS to the prepareStatement call
    try (PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        int affectedRows = pst.executeUpdate();
        
        if (affectedRows > 0) {
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Returns the actual b_id from SQLite
                }
            }
        }
        return affectedRows; 
    } catch (SQLException ex) {
        System.out.println("Insert Error: " + ex.getMessage());
        return 0;
    }
}
    
    // Method to update or delete data in the database
public boolean updateData(String sql) {
    try {
        java.sql.PreparedStatement pst = connect.prepareStatement(sql);
        int rows = pst.executeUpdate();
        pst.close();
        return rows > 0;
    } catch (java.sql.SQLException ex) {
        System.out.println("Update Error: " + ex.getMessage());
        return false;
    }
}
    
    
}