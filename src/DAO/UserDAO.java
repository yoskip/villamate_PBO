package DAO;

import KONEKSI.KoneksiDB;
import Model.User;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserDAO {

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY id_user";

        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id_user"));
                u.setUsername(rs.getString("username"));
                u.setNama(rs.getString("nama"));
                u.setRole(rs.getString("role"));
                list.add(u);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return list;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    int userId = rs.getInt("id_user");

                    if (checkPassword(password, storedHash)) {
                        User u = new User();
                        u.setId(userId);
                        u.setUsername(rs.getString("username"));
                        u.setNama(rs.getString("nama"));
                        u.setRole(rs.getString("role"));
                        return u;
                    }

                    String sqlMigration = "SELECT * FROM user WHERE id_user = ? AND password = SHA2(?, 256)";
                    try (PreparedStatement ps2 = conn.prepareStatement(sqlMigration)) {
                        ps2.setInt(1, userId);
                        ps2.setString(2, password);
                        try (ResultSet rs2 = ps2.executeQuery()) {
                            if (rs2.next()) {
                                updatePassword(userId, password);
                                User u = new User();
                                u.setId(userId);
                                u.setUsername(rs2.getString("username"));
                                u.setNama(rs2.getString("nama"));
                                u.setRole(rs2.getString("role"));
                                return u;
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return null;
    }

    public User getByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id_user"));
                    u.setUsername(rs.getString("username"));
                    u.setNama(rs.getString("nama"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public boolean addUser(User u) {
        String sql = "INSERT INTO user (username, password, nama, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, hashPassword(u.getPassword()));
            ps.setString(3, u.getNama());
            ps.setString(4, u.getRole());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM user WHERE id_user = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePassword(int id, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE id_user = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashPassword(newPassword));
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            byte[] hashWithSalt = new byte[salt.length + hash.length];
            System.arraycopy(salt, 0, hashWithSalt, 0, salt.length);
            System.arraycopy(hash, 0, hashWithSalt, salt.length, hash.length);

            return Base64.getEncoder().encodeToString(hashWithSalt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    private boolean checkPassword(String password, String storedHash) {
        try {
            byte[] decoded = Base64.getDecoder().decode(storedHash);
            byte[] salt = new byte[16];
            byte[] hash = new byte[decoded.length - 16];
            System.arraycopy(decoded, 0, salt, 0, 16);
            System.arraycopy(decoded, 16, hash, 0, hash.length);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] computedHash = factory.generateSecret(spec).getEncoded();

            return java.security.MessageDigest.isEqual(hash, computedHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IllegalArgumentException e) {
            return false;
        }
    }
}
