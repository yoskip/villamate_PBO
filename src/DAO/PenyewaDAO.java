package DAO;

import KONEKSI.KoneksiDB;
import Model.Penyewa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PenyewaDAO {

    public List<Penyewa> getAllPenyewa() {
        List<Penyewa> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewa ORDER BY id_penyewa";

        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Penyewa p = new Penyewa();
                p.setId(rs.getInt("id_penyewa"));
                p.setNama(rs.getString("nama"));
                p.setNoKtp(rs.getString("no_ktp"));
                p.setNoHp(rs.getString("no_hp"));
                p.setAlamat(rs.getString("alamat"));
                list.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return list;
    }

    public int addPenyewa(Penyewa p) {
        String sql = "INSERT INTO penyewa (nama, no_ktp, no_hp, alamat) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoKtp());
            ps.setString(3, p.getNoHp());
            ps.setString(4, p.getAlamat());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) generatedId = rs.getInt(1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return generatedId;
    }

    public Penyewa getPenyewaByNoKtp(String noKtp) {
        String sql = "SELECT * FROM penyewa WHERE no_ktp = ?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, noKtp);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Penyewa p = new Penyewa();
                    p.setId(rs.getInt("id_penyewa"));
                    p.setNama(rs.getString("nama"));
                    p.setNoKtp(rs.getString("no_ktp"));
                    p.setNoHp(rs.getString("no_hp"));
                    p.setAlamat(rs.getString("alamat"));
                    return p;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePenyewa(Penyewa p) {
        String sql = "UPDATE penyewa SET nama=?, no_ktp=?, no_hp=?, alamat=? WHERE id_penyewa=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoKtp());
            ps.setString(3, p.getNoHp());
            ps.setString(4, p.getAlamat());
            ps.setInt(5, p.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePenyewa(int id) {
        String sql = "DELETE FROM penyewa WHERE id_penyewa=?";

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
}
