package DAO;

import KONEKSI.KoneksiDB;
import Model.Villa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class VillaDAO {

    public List<Villa> getAllVilla() {
        List<Villa> list = new ArrayList<>();
        String sql = "SELECT * FROM villa ORDER BY id_villa";

        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Villa v = new Villa();
                v.setId(rs.getInt("id_villa"));
                v.setNama(rs.getString("nama_villa"));
                v.setKapasitas(rs.getInt("kapasitas"));
                v.setHarga(rs.getDouble("harga_per_hari"));
                v.setStatus(rs.getString("status"));
                v.setDeskripsi(rs.getString("deskripsi"));
                v.setGambar(rs.getString("gambar"));
                list.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error VillaDAO: " + e.getMessage());
        }
        return list;
    }

    public boolean insertVilla(Villa v) {
        String sql = "INSERT INTO villa (nama_villa, kapasitas, harga_per_hari, status, deskripsi, gambar) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getNama());
            ps.setInt(2, v.getKapasitas());
            ps.setDouble(3, v.getHarga());
            ps.setString(4, v.getStatus());
            ps.setString(5, v.getDeskripsi());
            ps.setString(6, v.getGambar());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error VillaDAO: " + e.getMessage());
            return false;
        }
    }

    public boolean updateVilla(Villa v) {
        String sql = "UPDATE villa SET nama_villa=?, kapasitas=?, harga_per_hari=?, status=?, deskripsi=?, gambar=? WHERE id_villa=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getNama());
            ps.setInt(2, v.getKapasitas());
            ps.setDouble(3, v.getHarga());
            ps.setString(4, v.getStatus());
            ps.setString(5, v.getDeskripsi());
            ps.setString(6, v.getGambar());
            ps.setInt(7, v.getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error VillaDAO: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVilla(int id) {
        String sql = "DELETE FROM villa WHERE id_villa=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println("Error VillaDAO: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE villa SET status=? WHERE id_villa=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error VillaDAO: " + e.getMessage());
            return false;
        }
    }

    public Villa getVillaById(int id) {
        String sql = "SELECT * FROM villa WHERE id_villa = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Villa v = new Villa();
                    v.setId(rs.getInt("id_villa"));
                    v.setNama(rs.getString("nama_villa"));
                    v.setKapasitas(rs.getInt("kapasitas"));
                    v.setHarga(rs.getDouble("harga_per_hari"));
                    v.setStatus(rs.getString("status"));
                    v.setDeskripsi(rs.getString("deskripsi"));
                    v.setGambar(rs.getString("gambar"));
                    return v;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error VillaDAO: " + e.getMessage());
        }
        return null;
    }
}
