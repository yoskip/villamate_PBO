package GUI;

import DAO.PenyewaDAO;
import DAO.TransaksiDAO;
import DAO.UserDAO;
import DAO.VillaDAO;
import Model.User;
import Model.Villa;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

public class DashboardAdminFrame extends javax.swing.JFrame {

    private VillaDAO villaDAO = new VillaDAO();
    private PenyewaDAO penyewaDAO = new PenyewaDAO();
    private UserDAO userDAO = new UserDAO();
    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private User currentUser;

    public DashboardAdminFrame() {
        this(null);
    }

    public DashboardAdminFrame(User user) {
        this.currentUser = user;
        initComponents();

        // Runtime settings scrollpane
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(new Color(240, 245, 240));

        int completed = transaksiDAO.autoCompleteTransactions();
        loadVillaCards();
        loadStatistik();
        checkRole();
    }

    private void checkRole() {
        if (currentUser != null && "petugas".equals(currentUser.getRole())) {
            btnKelolaVilla.setVisible(false);
            btnDataPenyewa.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnKelolaVilla = new javax.swing.JButton();
        btnDataPenyewa = new javax.swing.JButton();
        btnKelolaTransaksi = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTotalVilla = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblTotalPenyewa = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblTotalAdmin = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        villaPanel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTransaksiAktif = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VillaMate - Dashboard Admin");

        jPanel6.setBackground(new java.awt.Color(0, 0, 102));

        jButton1.setText("Dashboard");

        btnKelolaVilla.setText("Kelola Villa");
        btnKelolaVilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaVillaActionPerformed(evt);
            }
        });

        btnDataPenyewa.setText("Data Penyewa");
        btnDataPenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPenyewaActionPerformed(evt);
            }
        });

        btnKelolaTransaksi.setText("Kelola Transaksi");
        btnKelolaTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaTransaksiActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Villa Mate");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnKelolaVilla, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnDataPenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnKelolaTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addGap(56, 56, 56)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKelolaVilla, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDataPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKelolaTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel5.setBackground(new java.awt.Color(60, 179, 113));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Total Villa");

        lblTotalVilla.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblTotalVilla.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalVilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalVilla.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalVilla, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(lblTotalVilla)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(30, 107, 165));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Total Penyewa");

        lblTotalPenyewa.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblTotalPenyewa.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalPenyewa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPenyewa.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addComponent(lblTotalPenyewa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(230, 123, 35));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Pendapatan");

        lblTotalAdmin.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lblTotalAdmin.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalAdmin.setText("Rp 0");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(lblTotalAdmin)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        scrollPane.setBorder(null);

        villaPanel.setBackground(new java.awt.Color(240, 245, 240));
        villaPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 20));
        scrollPane.setViewportView(villaPanel);

        jPanel7.setBackground(new java.awt.Color(30, 107, 165));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Transaksi Aktif");

        lblTransaksiAktif.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblTransaksiAktif.setForeground(new java.awt.Color(255, 255, 255));
        lblTransaksiAktif.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTransaksiAktif.setText("0");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTransaksiAktif, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7)
                .addGap(10, 10, 10)
                .addComponent(lblTransaksiAktif)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPane))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnKelolaVillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaVillaActionPerformed
        KelolaVillaFrameAdmin kelola = new KelolaVillaFrameAdmin(currentUser);
        kelola.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKelolaVillaActionPerformed

    private void btnDataPenyewaActionPerformed(java.awt.event.ActionEvent evt) {
        new DataPenyewaFrameAdmin(currentUser).setVisible(true);
        this.dispose();
    }

    private void btnKelolaTransaksiActionPerformed(java.awt.event.ActionEvent evt) {
        new KelolaTransaksiFrameAdmin(currentUser).setVisible(true);
        this.dispose();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LandingPage().setVisible(true);
        this.dispose();
    }

    private void loadStatistik() {
        lblTotalVilla.setText(String.valueOf(villaDAO.getAllVilla().size()));
        lblTotalPenyewa.setText(String.valueOf(penyewaDAO.getAllPenyewa().size()));
        lblTotalAdmin.setText("Rp " + NumberFormat.getNumberInstance(Locale.of("id", "ID")).format((long) transaksiDAO.getTotalPendapatan()));

        lblTransaksiAktif.setText(String.valueOf(transaksiDAO.getJumlahTransaksiAktif()));
    }

    private void loadVillaCards() {
        villaPanel.removeAll();
        List<Villa> daftarVilla = villaDAO.getAllVilla();

        if (daftarVilla.isEmpty()) {
            JLabel kosong = new JLabel("Belum ada villa tersedia.");
            kosong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            kosong.setForeground(new Color(100, 100, 100));
            villaPanel.add(kosong);
        } else {
            for (Villa v : daftarVilla) {
                villaPanel.add(buatKartuVilla(v));
            }
        }

        villaPanel.revalidate();
        villaPanel.repaint();
    }

    private JPanel buatKartuVilla(Villa v) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(225, 310));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel gambarLabel = new JLabel();
        gambarLabel.setPreferredSize(new Dimension(200, 110));
        gambarLabel.setHorizontalAlignment(JLabel.CENTER);
        if (v.getGambar() != null && !v.getGambar().isEmpty()) {
            ImageIcon icon = new ImageIcon(v.getGambar());
            Image img = icon.getImage().getScaledInstance(200, 110, Image.SCALE_SMOOTH);
            gambarLabel.setIcon(new ImageIcon(img));
        } else {
            gambarLabel.setText("(No Image)");
            gambarLabel.setForeground(new Color(180, 180, 180));
            gambarLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            gambarLabel.setOpaque(true);
            gambarLabel.setBackground(new Color(245, 245, 245));
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JLabel badge = new JLabel(v.getStatus());
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        boolean tersedia = "Tersedia".equalsIgnoreCase(v.getStatus());
        badge.setForeground(tersedia ? new Color(46, 125, 50) : new Color(180, 0, 0));
        badge.setOpaque(true);
        badge.setBackground(tersedia ? new Color(232, 245, 233) : new Color(255, 235, 235));
        topPanel.add(badge, BorderLayout.EAST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel namaLabel = new JLabel(v.getNama());
        namaLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        namaLabel.setForeground(new Color(25, 25, 25));

        JLabel kapasitasLabel = new JLabel("👤 " + v.getKapasitas() + " Tamu");
        kapasitasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        kapasitasLabel.setForeground(new Color(130, 130, 130));

        NumberFormat rupiahFormat = NumberFormat.getNumberInstance(Locale.of("id", "ID"));
        String hargaStr = "Rp " + rupiahFormat.format((long) v.getHarga()) + " / malam";
        JLabel hargaLabel = new JLabel(hargaStr);
        hargaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        hargaLabel.setForeground(new Color(30, 58, 95));

        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(namaLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(kapasitasLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(hargaLabel);

        JButton btnSewa = new JButton("Sewa Sekarang");
        btnSewa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSewa.setBackground(tersedia ? new Color(46, 125, 50) : new Color(180, 180, 180));
        btnSewa.setForeground(Color.WHITE);
        btnSewa.setFocusPainted(false);
        btnSewa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSewa.setEnabled(tersedia);
        btnSewa.addActionListener(e -> {
            SewaDialog dialog = new SewaDialog(DashboardAdminFrame.this, v);
            dialog.setVisible(true);
            loadVillaCards();
            loadStatistik();
        });

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        card.add(gambarLabel, BorderLayout.NORTH);
        card.add(contentPanel, BorderLayout.CENTER);
        card.add(btnSewa, BorderLayout.SOUTH);

        return card;
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DashboardAdminFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDataPenyewa;
    private javax.swing.JButton btnKelolaTransaksi;
    private javax.swing.JButton btnKelolaVilla;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblTotalAdmin;
    private javax.swing.JLabel lblTotalPenyewa;
    private javax.swing.JLabel lblTotalVilla;
    private javax.swing.JLabel lblTransaksiAktif;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel villaPanel;
    // End of variables declaration//GEN-END:variables
}