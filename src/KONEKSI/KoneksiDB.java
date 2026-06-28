package KONEKSI;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class KoneksiDB {

    private static String URL;
    private static String USER;
    private static String PASS;

    static {
        loadConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver tidak ditemukan: " + e.getMessage());
        }
    }

    private static void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            props.load(input);
            URL = props.getProperty("db.url", "jdbc:mysql://localhost:3306/villamate");
            USER = props.getProperty("db.user", "root");
            PASS = props.getProperty("db.password", "");
        } catch (Exception e) {
            URL = "jdbc:mysql://localhost:3306/villamate";
            USER = "root";
            PASS = "";
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException("Gagal koneksi database: " + e.getMessage());
        }
    }
}
