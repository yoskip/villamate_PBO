package GUI;

import DAO.VillaDAO;
import Model.Villa;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.*;

public class LandingPage extends javax.swing.JFrame {

    private VillaDAO villaDAO = new VillaDAO();

    public LandingPage() {
        initComponents();

        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(240, 245, 240));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Grid 3 kolom, jarak antar kartu 20px horizontal dan 20px vertikal
        villaPanel.setLayout(new GridLayout(0, 3, 20, 20));
        villaPanel.setBackground(new Color(240, 245, 240));
        villaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        loadVillaCards();

        jPanel2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 225, 220)));

        // Ukuran window cukup untuk 3 kolom kartu
        setMinimumSize(new Dimension(1000, 700));
        setSize(1100, 800);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        TittleSection1 = new javax.swing.JLabel();
        SubtitleSection = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        NavBeranda = new javax.swing.JLabel();
        NavVilla = new javax.swing.JLabel();
        ButtonLogin = new javax.swing.JButton();
        TittleSection = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        villaPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VillaMate - Sewa Villa Impian Anda");

        jPanel1.setBackground(new java.awt.Color(240, 245, 240));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(26, 50, 82));

        TittleSection1.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        TittleSection1.setForeground(new java.awt.Color(255, 255, 255));
        TittleSection1.setText("Sewa Villa Impian Anda");

        SubtitleSection.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        SubtitleSection.setForeground(new java.awt.Color(180, 195, 210));
        SubtitleSection.setText("Temukan villa terbaik untuk liburan Anda bersama VillaMate");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TittleSection1)
                    .addComponent(SubtitleSection))
                .addContainerGap(721, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(TittleSection1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(SubtitleSection)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        Logo.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        Logo.setText("VillaMate");

        NavBeranda.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NavBeranda.setForeground(new java.awt.Color(26, 50, 82));
        NavBeranda.setText("Beranda");

        NavVilla.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        NavVilla.setForeground(new java.awt.Color(120, 120, 120));
        NavVilla.setText("Villa");

        ButtonLogin.setBackground(new java.awt.Color(46, 125, 50));
        ButtonLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ButtonLogin.setForeground(new java.awt.Color(255, 255, 255));
        ButtonLogin.setText("Login Admin");
        ButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(Logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(NavBeranda)
                .addGap(16, 16, 16)
                .addComponent(NavVilla)
                .addGap(16, 16, 16)
                .addComponent(ButtonLogin)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NavBeranda)
                    .addComponent(NavVilla)
                    .addComponent(ButtonLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TittleSection.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        TittleSection.setText("Daftar Villa");

        // villaPanel akan diatur ulang di constructor (GridLayout 3 kolom)
        villaPanel.setBackground(new java.awt.Color(240, 245, 240));
        scrollPane.setViewportView(villaPanel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TittleSection)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(TittleSection, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loadVillaCards() {
        villaPanel.removeAll();

        List<Villa> daftarVilla = villaDAO.getAllVilla();

        if (daftarVilla.isEmpty()) {
            JLabel kosong = new JLabel("Belum ada villa tersedia.");
            kosong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            kosong.setForeground(new Color(100, 100, 100));
            kosong.setHorizontalAlignment(SwingConstants.CENTER);
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
        // Kartu utama: BorderLayout, background putih, border tipis
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));

        // ── GAMBAR (NORTH) ──────────────────────────────────────────────
        JLabel gambarLabel = new JLabel();
        gambarLabel.setPreferredSize(new Dimension(0, 160));
        gambarLabel.setMinimumSize(new Dimension(0, 160));
        gambarLabel.setHorizontalAlignment(JLabel.CENTER);
        gambarLabel.setOpaque(true);
        gambarLabel.setBackground(new Color(240, 242, 245));

        if (v.getGambar() != null && !v.getGambar().isEmpty()) {
            ImageIcon icon = new ImageIcon(v.getGambar());
            // Scale ke lebar penuh kartu, tinggi 160
            Image img = icon.getImage().getScaledInstance(-1, 160, Image.SCALE_SMOOTH);
            gambarLabel.setIcon(new ImageIcon(img));
        } else {
            gambarLabel.setText("🏡  No Image");
            gambarLabel.setForeground(new Color(180, 180, 180));
            gambarLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }

        // ── KONTEN TENGAH ────────────────────────────────────────────────
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        // Badge status di pojok kanan atas konten
        boolean tersedia = "Tersedia".equalsIgnoreCase(v.getStatus());
        JLabel badge = new JLabel(v.getStatus());
        badge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badge.setForeground(tersedia ? new Color(27, 94, 32) : new Color(183, 28, 28));
        badge.setOpaque(true);
        badge.setBackground(tersedia ? new Color(232, 245, 233) : new Color(255, 235, 238));
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(tersedia ? new Color(165, 214, 167) : new Color(239, 154, 154), 1),
                BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));

        JPanel badgeRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        badgeRow.setBackground(Color.WHITE);
        badgeRow.add(badge);

        // Info panel: nama, kapasitas, separator, harga, deskripsi
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel namaLabel = new JLabel(v.getNama());
        namaLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        namaLabel.setForeground(new Color(20, 20, 20));
        namaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel kapasitasLabel = new JLabel("  " + v.getKapasitas() + " Tamu");
        kapasitasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        kapasitasLabel.setForeground(new Color(120, 120, 120));
        kapasitasLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setForeground(new Color(230, 230, 230));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);

        NumberFormat rupiahFormat = NumberFormat.getNumberInstance(Locale.of("id", "ID"));
        JLabel hargaLabel = new JLabel("Rp " + rupiahFormat.format((long) v.getHarga()) + " / malam");
        hargaLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        hargaLabel.setForeground(new Color(26, 50, 82));
        hargaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Deskripsi dengan word-wrap via HTML, lebar max mengikuti kartu
        JLabel deskLabel = new JLabel("<html><div style='width:200px;color:#888;font-size:11px;'>"
                + v.getDeskripsi() + "</div></html>");
        deskLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        deskLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(namaLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(kapasitasLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(sep);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(hargaLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(deskLabel);

        contentPanel.add(badgeRow, BorderLayout.NORTH);
        contentPanel.add(infoPanel, BorderLayout.CENTER);

        // ── TOMBOL SEWA (SOUTH) ──────────────────────────────────────────
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(235, 235, 235)),
                BorderFactory.createEmptyBorder(10, 14, 12, 14)
        ));

        JButton btnSewa = new JButton(tersedia ? "Sewa Sekarang" : "Tidak Tersedia");
        btnSewa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnSewa.setBackground(tersedia ? new Color(26, 50, 82) : new Color(200, 200, 200));
        btnSewa.setForeground(Color.WHITE);
        btnSewa.setFocusPainted(false);
        btnSewa.setBorderPainted(false);
        btnSewa.setCursor(tersedia ? new Cursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
        btnSewa.setEnabled(tersedia);
        btnSewa.setPreferredSize(new Dimension(Integer.MAX_VALUE, 36));
        btnSewa.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        btnSewa.addActionListener(e -> {
            SewaDialog dialog = new SewaDialog(LandingPage.this, v);
            dialog.setVisible(true);
            loadVillaCards();
        });

        bottomPanel.add(btnSewa, BorderLayout.CENTER);

        // ── SUSUN KARTU ──────────────────────────────────────────────────
        card.add(gambarLabel, BorderLayout.NORTH);
        card.add(contentPanel, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void ButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {
        LoginAdmin dialog = new LoginAdmin(LandingPage.this);
        dialog.setVisible(true);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LandingPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LandingPage().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonLogin;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel NavBeranda;
    private javax.swing.JLabel NavVilla;
    private javax.swing.JLabel SubtitleSection;
    private javax.swing.JLabel TittleSection;
    private javax.swing.JLabel TittleSection1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JPanel villaPanel;
    // End of variables declaration//GEN-END:variables
}