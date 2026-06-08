package GUI;

import DAO.PenyewaDAO;
import DAO.TransaksiDAO;
import DAO.VillaDAO;
import Model.Penyewa;
import Model.Transaksi;
import Model.Villa;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import javax.swing.*;

public class SewaDialog extends JDialog {

    private Villa villa;
    private PenyewaDAO penyewaDAO = new PenyewaDAO();
    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private VillaDAO villaDAO = new VillaDAO();

    private static final NumberFormat RUPIAH = NumberFormat.getNumberInstance(Locale.of("id", "ID"));

    public SewaDialog(Window owner, Villa villa) {
        super(owner, "Sewa Villa", ModalityType.APPLICATION_MODAL);
        this.villa = villa;
        initComponents();
        setResizable(true);
        setMinimumSize(new Dimension(420, 480));
        setSize(460, 540);
        setLocationRelativeTo(owner);
        isiInfoVilla();
        aturSpinnerDate();
        initAutoFill();
    }

    private void isiInfoVilla() {
        lblVillaNilaiNama.setText(villa.getNama());
        lblVillaNilaiKapasitas.setText(villa.getKapasitas() + " Tamu");
        lblVillaNilaiHarga.setText("Rp " + RUPIAH.format((long) villa.getHarga()) + " / malam");
    }

    private void aturSpinnerDate() {
        Date today = new Date();

        SpinnerDateModel modelCheckin = new SpinnerDateModel(today, today, null, java.util.Calendar.DAY_OF_MONTH);
        spnCheckin.setModel(modelCheckin);
        spnCheckin.setEditor(new JSpinner.DateEditor(spnCheckin, "dd/MM/yyyy"));

        java.util.Calendar besok = java.util.Calendar.getInstance();
        besok.add(java.util.Calendar.DAY_OF_MONTH, 1);
        SpinnerDateModel modelCheckout = new SpinnerDateModel(besok.getTime(), besok.getTime(), null, java.util.Calendar.DAY_OF_MONTH);
        spnCheckout.setModel(modelCheckout);
        spnCheckout.setEditor(new JSpinner.DateEditor(spnCheckout, "dd/MM/yyyy"));

        spnCheckin.addChangeListener(e -> {
            Date checkinVal = (Date) spnCheckin.getValue();
            java.util.Calendar minCheckout = java.util.Calendar.getInstance();
            minCheckout.setTime(checkinVal);
            minCheckout.add(java.util.Calendar.DAY_OF_MONTH, 1);
            ((SpinnerDateModel) spnCheckout.getModel()).setStart(minCheckout.getTime());
            if (((Date) spnCheckout.getValue()).before(minCheckout.getTime())) {
                spnCheckout.setValue(minCheckout.getTime());
            }
            hitungTotal();
        });
        spnCheckout.addChangeListener(e -> hitungTotal());
    }

    private void hitungTotal() {
        Date checkin = (Date) spnCheckin.getValue();
        Date checkout = (Date) spnCheckout.getValue();
        if (checkin != null && checkout != null && checkout.after(checkin)) {
            long diff = checkout.getTime() - checkin.getTime();
            int hari = (int) (diff / (1000 * 60 * 60 * 24));
            lblNilaiTotalHari.setText(hari + " hari");
            double total = hari * villa.getHarga();
            lblNilaiTotalBiaya.setText("Rp " + RUPIAH.format((long) total));
        } else {
            lblNilaiTotalHari.setText("0 hari");
            lblNilaiTotalBiaya.setText("Rp 0");
        }
    }

    private void simpanSewa() {
        String nama = txtNama.getText().trim();
        String noKtp = txtNoKtp.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String alamat = txtAlamat.getText().trim();

        if (nama.isEmpty() || noKtp.isEmpty() || noHp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua data penyewa!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nama.length() < 3) {
            JOptionPane.showMessageDialog(this, "Nama minimal 3 karakter!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!noKtp.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, "No. KTP harus 16 digit angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!noHp.matches("^(08|\\+62)\\d{8,12}$")) {
            JOptionPane.showMessageDialog(this, "No. HP tidak valid! Contoh: 081234567890", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date checkin = (Date) spnCheckin.getValue();
        Date checkout = (Date) spnCheckout.getValue();
        if (checkin == null || checkout == null || !checkout.after(checkin)) {
            JOptionPane.showMessageDialog(this, "Tanggal checkout harus setelah check-in!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Date today = java.sql.Date.valueOf(LocalDate.now());
        if (checkin.before(today)) {
            JOptionPane.showMessageDialog(this, "Tanggal check-in tidak boleh di masa lalu!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (totalHari > 30) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Penyewaan lebih dari 30 hari (" + totalHari + " hari). Lanjutkan?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        if (transaksiDAO.isTanggalKonflik(villa.getId(), checkin, checkout)) {
            JOptionPane.showMessageDialog(this, "Villa sudah dipesan orang lain di tanggal tersebut!", "Konflik Jadwal", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double totalBiaya = totalHari * villa.getHarga();

        try {
            Penyewa penyewa = penyewaDAO.getPenyewaByNoKtp(noKtp);
            if (penyewa == null) {
                penyewa = new Penyewa(0, nama, noKtp, noHp, alamat);
                int idPenyewa = penyewaDAO.addPenyewa(penyewa);
                if (idPenyewa == -1) {
                    return;
                }
                penyewa.setId(idPenyewa);
            }

            Transaksi t = new Transaksi();
            t.setIdVilla(villa.getId());
            t.setIdPenyewa(penyewa.getId());
            t.setTglCheckin(checkin);
            t.setTglCheckout(checkout);
            t.setTotalHari(totalHari);
            t.setTotalBiaya(totalBiaya);
            t.setStatus("Aktif");

            if (!transaksiDAO.addTransaksi(t)) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!villaDAO.updateStatus(villa.getId(), "Tidak Tersedia")) {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui status villa!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "Villa berhasil disewa!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initAutoFill() {
        txtNoKtp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String ktp = txtNoKtp.getText().trim();
                if (ktp.length() == 16 && ktp.matches("\\d{16}")) {
                    Penyewa p = penyewaDAO.getPenyewaByNoKtp(ktp);
                    if (p != null) {
                        txtNama.setText(p.getNama());
                        txtNoHp.setText(p.getNoHp());
                        txtAlamat.setText(p.getAlamat());
                        txtNama.setEnabled(false);
                        txtNoHp.setEnabled(false);
                        txtAlamat.setEnabled(false);
                    } else {
                        txtNama.setEnabled(true);
                        txtNoHp.setEnabled(true);
                        txtAlamat.setEnabled(true);
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        villaPanel = new JPanel();
        lblVillaNama = new JLabel();
        lblVillaNilaiNama = new JLabel();
        lblVillaKapasitas = new JLabel();
        lblVillaNilaiKapasitas = new JLabel();
        lblVillaHarga = new JLabel();
        lblVillaNilaiHarga = new JLabel();
        penyewaPanel = new JPanel();
        lblNama = new JLabel();
        txtNama = new JTextField();
        lblNoKtp = new JLabel();
        txtNoKtp = new JTextField();
        lblNoHp = new JLabel();
        txtNoHp = new JTextField();
        lblAlamat = new JLabel();
        txtAlamat = new JTextField();
        tanggalPanel = new JPanel();
        lblCheckin = new JLabel();
        spnCheckin = new JSpinner();
        lblCheckout = new JLabel();
        spnCheckout = new JSpinner();
        lblTotalHari = new JLabel();
        lblNilaiTotalHari = new JLabel();
        lblTotalBiaya = new JLabel();
        lblNilaiTotalBiaya = new JLabel();
        btnSimpan = new JButton();
        btnBatal = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sewa Villa");

        jPanel1.setBackground(new Color(245, 247, 250));

        villaPanel.setBackground(Color.WHITE);
        villaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(null, "Informasi Villa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", Font.BOLD, 13), new Color(30, 58, 95)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        lblVillaNama.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVillaNama.setForeground(new Color(80, 80, 80));
        lblVillaNama.setText("Nama Villa");

        lblVillaNilaiNama.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblVillaNilaiNama.setForeground(new Color(25, 25, 25));
        lblVillaNilaiNama.setText("-");

        lblVillaKapasitas.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVillaKapasitas.setForeground(new Color(80, 80, 80));
        lblVillaKapasitas.setText("Kapasitas");

        lblVillaNilaiKapasitas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblVillaNilaiKapasitas.setForeground(new Color(25, 25, 25));
        lblVillaNilaiKapasitas.setText("-");

        lblVillaHarga.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblVillaHarga.setForeground(new Color(80, 80, 80));
        lblVillaHarga.setText("Harga");

        lblVillaNilaiHarga.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblVillaNilaiHarga.setForeground(new Color(46, 125, 50));
        lblVillaNilaiHarga.setText("-");

        GroupLayout villaPanelLayout = new GroupLayout(villaPanel);
        villaPanel.setLayout(villaPanelLayout);
        villaPanelLayout.setHorizontalGroup(
                villaPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(villaPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(villaPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblVillaNama)
                                        .addComponent(lblVillaKapasitas)
                                        .addComponent(lblVillaHarga))
                                .addGap(12, 12, 12)
                                .addGroup(villaPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(lblVillaNilaiNama)
                                        .addComponent(lblVillaNilaiKapasitas)
                                        .addComponent(lblVillaNilaiHarga))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        villaPanelLayout.setVerticalGroup(
                villaPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(villaPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(villaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblVillaNama)
                                        .addComponent(lblVillaNilaiNama))
                                .addGap(8, 8, 8)
                                .addGroup(villaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblVillaKapasitas)
                                        .addComponent(lblVillaNilaiKapasitas))
                                .addGap(8, 8, 8)
                                .addGroup(villaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblVillaHarga)
                                        .addComponent(lblVillaNilaiHarga))
                                .addContainerGap())
        );

        penyewaPanel.setBackground(Color.WHITE);
        penyewaPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(null, "Data Penyewa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", Font.BOLD, 13), new Color(30, 58, 95)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNama.setForeground(new Color(80, 80, 80));
        lblNama.setText("Nama");

        lblNoKtp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNoKtp.setForeground(new Color(80, 80, 80));
        lblNoKtp.setText("No. KTP");

        lblNoHp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNoHp.setForeground(new Color(80, 80, 80));
        lblNoHp.setText("No. HP");

        lblAlamat.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblAlamat.setForeground(new Color(80, 80, 80));
        lblAlamat.setText("Alamat");

        GroupLayout penyewaPanelLayout = new GroupLayout(penyewaPanel);
        penyewaPanel.setLayout(penyewaPanelLayout);
        penyewaPanelLayout.setHorizontalGroup(
                penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(penyewaPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblNama)
                                        .addComponent(lblNoKtp)
                                        .addComponent(lblNoHp)
                                        .addComponent(lblAlamat))
                                .addGap(12, 12, 12)
                                .addGroup(penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNama, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                        .addComponent(txtNoKtp, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                        .addComponent(txtNoHp, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                                        .addComponent(txtAlamat, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                .addContainerGap())
        );
        penyewaPanelLayout.setVerticalGroup(
                penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(penyewaPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNama)
                                        .addComponent(txtNama, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNoKtp)
                                        .addComponent(txtNoKtp, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNoHp)
                                        .addComponent(txtNoHp, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(penyewaPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblAlamat)
                                        .addComponent(txtAlamat, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        tanggalPanel.setBackground(Color.WHITE);
        tanggalPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(null, "Tanggal Sewa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", Font.BOLD, 13), new Color(30, 58, 95)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        lblCheckin.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCheckin.setForeground(new Color(80, 80, 80));
        lblCheckin.setText("Check-in");

        lblCheckout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCheckout.setForeground(new Color(80, 80, 80));
        lblCheckout.setText("Check-out");

        lblTotalHari.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotalHari.setForeground(new Color(80, 80, 80));
        lblTotalHari.setText("Total Hari");

        lblNilaiTotalHari.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblNilaiTotalHari.setForeground(new Color(25, 25, 25));
        lblNilaiTotalHari.setText("0 hari");

        lblTotalBiaya.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotalBiaya.setForeground(new Color(80, 80, 80));
        lblTotalBiaya.setText("Total Biaya");

        lblNilaiTotalBiaya.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNilaiTotalBiaya.setForeground(new Color(46, 125, 50));
        lblNilaiTotalBiaya.setText("Rp 0");

        GroupLayout tanggalPanelLayout = new GroupLayout(tanggalPanel);
        tanggalPanel.setLayout(tanggalPanelLayout);
        tanggalPanelLayout.setHorizontalGroup(
                tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(tanggalPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblCheckin)
                                        .addComponent(lblCheckout)
                                        .addComponent(lblTotalHari)
                                        .addComponent(lblTotalBiaya))
                                .addGap(12, 12, 12)
                                .addGroup(tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(spnCheckin, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(spnCheckout, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(lblNilaiTotalHari)
                                        .addComponent(lblNilaiTotalBiaya))
                                .addContainerGap())
        );
        tanggalPanelLayout.setVerticalGroup(
                tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(tanggalPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCheckin)
                                        .addComponent(spnCheckin, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblCheckout)
                                        .addComponent(spnCheckout, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTotalHari)
                                        .addComponent(lblNilaiTotalHari))
                                .addGap(8, 8, 8)
                                .addGroup(tanggalPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTotalBiaya)
                                        .addComponent(lblNilaiTotalBiaya))
                                .addContainerGap())
        );

        btnSimpan.setBackground(new Color(46, 125, 50));
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setText("Simpan Sewa");
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        btnSimpan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(evt -> btnSimpanActionPerformed(evt));

        btnBatal.setBackground(new Color(200, 200, 200));
        btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBatal.setForeground(new Color(60, 60, 60));
        btnBatal.setText("Batal");
        btnBatal.setFocusPainted(false);
        btnBatal.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        btnBatal.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBatal.addActionListener(evt -> btnBatalActionPerformed(evt));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(villaPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(penyewaPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tanggalPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnSimpan)
                                                .addGap(12, 12, 12)
                                                .addComponent(btnBatal)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(villaPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(penyewaPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(tanggalPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnSimpan)
                                        .addComponent(btnBatal))
                                .addGap(20, 20, 20))
        );

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(jPanel1);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(ActionEvent evt) {
        simpanSewa();
    }

    private void btnBatalActionPerformed(ActionEvent evt) {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JScrollPane scrollPane;
    private JPanel jPanel1;
    private JPanel villaPanel;
    private JLabel lblVillaNama;
    private JLabel lblVillaNilaiNama;
    private JLabel lblVillaKapasitas;
    private JLabel lblVillaNilaiKapasitas;
    private JLabel lblVillaHarga;
    private JLabel lblVillaNilaiHarga;
    private JPanel penyewaPanel;
    private JLabel lblNama;
    private JTextField txtNama;
    private JLabel lblNoKtp;
    private JTextField txtNoKtp;
    private JLabel lblNoHp;
    private JTextField txtNoHp;
    private JLabel lblAlamat;
    private JTextField txtAlamat;
    private JPanel tanggalPanel;
    private JLabel lblCheckin;
    private JSpinner spnCheckin;
    private JLabel lblCheckout;
    private JSpinner spnCheckout;
    private JLabel lblTotalHari;
    private JLabel lblNilaiTotalHari;
    private JLabel lblTotalBiaya;
    private JLabel lblNilaiTotalBiaya;
    private JButton btnSimpan;
    private JButton btnBatal;
    // End of variables declaration//GEN-END:variables
}
