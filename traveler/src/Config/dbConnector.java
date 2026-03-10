package config;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class dbConnector {
    private static dbConnector instance;
    private Connection connect;

    private dbConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:travelDB.db");
            
            // Log path to ensure you are looking at the right DB in SQLite Studio
            System.out.println(">>> DB Path: " + new File("travelDB.db").getAbsolutePath());
            
            enforceAdminAccount();
        } catch (Exception ex) {
            System.out.println("Connection Error: " + ex.getMessage());
        }
    }

    private void enforceAdminAccount() throws NoSuchAlgorithmException {
        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tbl_account WHERE a_id = 1")) {
            
            if (!rs.next()) {
                System.out.println(">>> Seeding Hashed Admin...");
                
                // Hashing the default password
                String hashedPassword = passHasher.hashPassword("admin123");
                
                String sql = "INSERT INTO tbl_account (a_id, firstname, lastname, username, email, password, contact, address, gender, type, image, status) " +
                             "VALUES (1, 'System', 'Admin', 'admin', 'admin@travel.com', '" + hashedPassword + "', '000', 'N/A', 'Other', 'Admin', '', 'Active')";
                
                stmt.executeUpdate(sql);
                System.out.println(">>> SUCCESS: Admin created with HASHED password.");
            }
        } catch (SQLException e) {
            System.out.println(">>> Seed Error: " + e.getMessage());
        }
    }

    public static synchronized dbConnector getInstance() {
        if (instance == null) instance = new dbConnector();
        return instance;
    }

    public Connection getConnection() { return connect; }

    public ResultSet getData(String query) throws SQLException {
        return connect.createStatement().executeQuery(query);
    }

    // Protection logic for ID 1
    public boolean updateData(String sql) {
        String query = sql.toLowerCase();
        if ((query.contains("delete") || query.contains("update")) && query.contains("a_id = 1")) {
            System.out.println(">>> SECURITY: Modification of Admin ID 1 is forbidden.");
            return false;
        }
        try (PreparedStatement pst = connect.prepareStatement(sql)) {
            return pst.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public int insertData(String sql) {
        try (PreparedStatement pst = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) return generatedKeys.getInt(1);
                }
            }
            return affectedRows; 
        } catch (SQLException ex) {
            System.out.println("Insert Error: " + ex.getMessage());
            return 0;
        }
    }
}