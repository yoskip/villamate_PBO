package DAO;

import KONEKSI.KoneksiDB;
import Model.Transaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {

    public List<Transaksi> getAllTransaksi() {
        List<Transaksi> list = new ArrayList<>();
        String sql = "SELECT * FROM transaksi ORDER BY id_transaksi";

        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaksi t = new Transaksi();
                t.setId(rs.getInt("id_transaksi"));
                t.setIdVilla(rs.getInt("id_villa"));
                t.setIdPenyewa(rs.getInt("id_penyewa"));
                t.setTglCheckin(rs.getDate("tgl_checkin"));
                t.setTglCheckout(rs.getDate("tgl_checkout"));
                t.setTotalHari(rs.getInt("total_hari"));
                t.setTotalBiaya(rs.getDouble("total_biaya"));
                t.setStatus(rs.getString("status"));
                t.setTanggalTransaksi(rs.getTimestamp("tanggal_transaksi"));
                list.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error getAllTransaksi: " + e.getMessage());
        }
        return list;
    }

    public boolean addTransaksi(Transaksi t) {
        String sql = "INSERT INTO transaksi (id_villa, id_penyewa, tgl_checkin, tgl_checkout, total_hari, total_biaya, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getIdVilla());
            ps.setInt(2, t.getIdPenyewa());
            ps.setDate(3, new java.sql.Date(t.getTglCheckin().getTime()));
            ps.setDate(4, new java.sql.Date(t.getTglCheckout().getTime()));
            ps.setInt(5, t.getTotalHari());
            ps.setDouble(6, t.getTotalBiaya());
            ps.setString(7, t.getStatus());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error addTransaksi: " + e.getMessage());
            return false;
        }
    }

    public void updateTransaksi(Transaksi t) {
        String sql = "UPDATE transaksi SET id_villa=?, id_penyewa=?, tgl_checkin=?, tgl_checkout=?, total_hari=?, total_biaya=?, status=? WHERE id_transaksi=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, t.getIdVilla());
            ps.setInt(2, t.getIdPenyewa());
            ps.setDate(3, new java.sql.Date(t.getTglCheckin().getTime()));
            ps.setDate(4, new java.sql.Date(t.getTglCheckout().getTime()));
            ps.setInt(5, t.getTotalHari());
            ps.setDouble(6, t.getTotalBiaya());
            ps.setString(7, t.getStatus());
            ps.setInt(8, t.getId());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Transaksi berhasil diupdate!");
        } catch (SQLException e) {
            System.err.println("Error updateTransaksi: " + e.getMessage());
        }
    }

    public boolean deleteTransaksi(int id) {
        String sql = "DELETE FROM transaksi WHERE id_transaksi=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleteTransaksi: " + e.getMessage());
            return false;
        }
    }

    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE transaksi SET status=? WHERE id_transaksi=?";

        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updateStatus: " + e.getMessage());
            return false;
        }
    }

    public double getTotalPendapatan() {
        String sql = "SELECT SUM(total_biaya) FROM transaksi WHERE status = 'Selesai'";
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            System.err.println("Error getTotalPendapatan: " + e.getMessage());
        }
        return 0;
    }

    public int getJumlahTransaksiAktif() {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE status = 'Aktif'";
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Error getJumlahTransaksiAktif: " + e.getMessage());
        }
        return 0;
    }

    public boolean isTanggalKonflik(int idVilla, java.util.Date checkin, java.util.Date checkout) {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE id_villa = ? AND status = 'Aktif' AND tgl_checkin < ? AND tgl_checkout > ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVilla);
            ps.setDate(2, new java.sql.Date(checkout.getTime()));
            ps.setDate(3, new java.sql.Date(checkin.getTime()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error isTanggalKonflik: " + e.getMessage());
        }
        return false;
    }

    public int autoCompleteTransactions() {
        String sql = "UPDATE transaksi SET status = 'Selesai' WHERE status = 'Aktif' AND tgl_checkout < CURDATE()";
        int updated = 0;
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement()) {
            updated = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error autoCompleteTransactions (update transaksi): " + e.getMessage());
        }

        String sqlVilla = "UPDATE villa v SET v.status = 'Tersedia' "
                + "WHERE EXISTS ("
                + "    SELECT 1 FROM transaksi t "
                + "    WHERE t.id_villa = v.id_villa "
                + "    AND t.status = 'Selesai' "
                + "    AND t.tgl_checkout < CURDATE()"
                + ") "
                + "AND NOT EXISTS ("
                + "    SELECT 1 FROM transaksi t "
                + "    WHERE t.id_villa = v.id_villa "
                + "    AND t.status = 'Aktif'"
                + ")";
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sqlVilla);
        } catch (SQLException e) {
            System.err.println("Error autoCompleteTransactions (update villa status): " + e.getMessage());
        }
        return updated;
    }
}
