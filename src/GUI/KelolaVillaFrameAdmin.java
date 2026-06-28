package GUI;

import DAO.VillaDAO;
import Model.User;
import Model.Villa;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class KelolaVillaFrameAdmin extends javax.swing.JFrame {

    private VillaDAO villaDAO = new VillaDAO();
    private User currentUser;
    private DefaultTableModel tableModel;

    public KelolaVillaFrameAdmin() {
        this(null);
    }

    public KelolaVillaFrameAdmin(User user) {
        this.currentUser = user;
        initComponents();

        if (currentUser != null && "petugas".equals(currentUser.getRole())) {
            JOptionPane.showMessageDialog(this, "Anda tidak memiliki akses ke halaman ini!", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
            new DashboardAdminFrame(currentUser).setVisible(true);
            this.dispose();
            return;
        }

        initTableLayout();
        loadDataToTable();
    }

    private void initTableLayout() {
        tableModel = (DefaultTableModel) tblVilla.getModel();
        tblVilla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblVilla.setRowSorter(sorter);

        txtSearch.putClientProperty("JTextField.placeholderText", "Cari villa...");
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtSearch.getText().trim();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<Villa> daftarVilla = villaDAO.getAllVilla();

        for (Villa v : daftarVilla) {
            Object[] row = {
                v.getId(),
                v.getNama(),
                v.getKapasitas(),
                v.getHarga(),
                v.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        txtNama.setText("");
        txtKapasitas.setText("");
        txtHarga.setText("");
        txtDeskripsi.setText("");
        txtGambar.setText("");
        cmbStatus.setSelectedIndex(0);
        tblVilla.clearSelection();
    }

    private boolean validasiFormVilla() {
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama villa tidak boleh kosong!");
            return false;
        }
        try {
            int kapasitas = Integer.parseInt(txtKapasitas.getText().trim());
            if (kapasitas <= 0) {
                JOptionPane.showMessageDialog(this, "Kapasitas harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kapasitas harus berupa angka!");
            return false;
        }
        try {
            double harga = Double.parseDouble(txtHarga.getText().trim());
            if (harga <= 0) {
                JOptionPane.showMessageDialog(this, "Harga harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            return false;
        }
        return true;
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
        jLabel1 = new javax.swing.JLabel();
        jLabelTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVilla = new javax.swing.JTable();
        jLabelNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabelKapasitas = new javax.swing.JLabel();
        txtKapasitas = new javax.swing.JTextField();
        jLabelHarga = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabelStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabelDeskripsi = new javax.swing.JLabel();
        scrollDeskripsi = new javax.swing.JScrollPane();
        txtDeskripsi = new javax.swing.JTextArea();
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabelGambar = new javax.swing.JLabel();
        txtGambar = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VillaMate - Kelola Data Villa");
        setPreferredSize(new java.awt.Dimension(1030, 760));

        jPanel6.setBackground(new java.awt.Color(0, 0, 102));

        jButton1.setText("Dashboard");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnKelolaVilla.setText("Kelola Villa");

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Villa Mate");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(btnKelolaVilla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDataPenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKelolaTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(67, 67, 67)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKelolaVilla, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDataPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnKelolaTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(30, 58, 95));
        jLabelTitle.setText("Kelola Data Villa");

        tblVilla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama Villa", "Kapasitas", "Harga", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblVilla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblVillaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblVilla);

        jLabelNama.setText("Nama Villa");

        jLabelKapasitas.setText("Kapasitas (Orang)");

        jLabelHarga.setText("Harga per Malam");

        jLabelStatus.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tersedia", "Tidak Tersedia" }));

        jLabelDeskripsi.setText("Deskripsi");

        txtDeskripsi.setColumns(20);
        txtDeskripsi.setRows(3);
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setWrapStyleWord(true);
        scrollDeskripsi.setViewportView(txtDeskripsi);

        btnTambah.setBackground(new java.awt.Color(46, 125, 50));
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("Tambah (Insert)");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(30, 107, 165));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Ubah (Update)");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(180, 0, 0));
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Hapus (Delete)");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabelGambar.setText("Gambar (path)");

        btnBrowse.setText("Cari...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelNama)
                            .addComponent(txtNama)
                            .addComponent(jLabelKapasitas)
                            .addComponent(txtKapasitas)
                            .addComponent(jLabelHarga)
                            .addComponent(txtHarga)
                            .addComponent(jLabelStatus)
                            .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelDeskripsi)
                            .addComponent(scrollDeskripsi)
                            .addComponent(jLabelGambar)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtGambar)
                                .addGap(0, 0, 0)
                                .addComponent(btnBrowse))
                            .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch)))
                    .addComponent(jLabelTitle))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelTitle)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelNama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelKapasitas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKapasitas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelHarga)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelDeskripsi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollDeskripsi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabelGambar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBrowse, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblVillaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblVillaMouseClicked
        int viewRow = tblVilla.getSelectedRow();
        if (viewRow != -1) {
            int modelRow = tblVilla.convertRowIndexToModel(viewRow);
            txtNama.setText(tableModel.getValueAt(modelRow, 1).toString());
            txtKapasitas.setText(tableModel.getValueAt(modelRow, 2).toString());
            txtHarga.setText(tableModel.getValueAt(modelRow, 3).toString());
            cmbStatus.setSelectedItem(tableModel.getValueAt(modelRow, 4).toString());

            Villa selected = villaDAO.getVillaById((int) tableModel.getValueAt(modelRow, 0));
            if (selected != null) {
                txtDeskripsi.setText(selected.getDeskripsi());
                txtGambar.setText(selected.getGambar());
            }
        }
    }//GEN-LAST:event_tblVillaMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if (!validasiFormVilla()) return;
        try {
            Villa v = new Villa();
            v.setNama(txtNama.getText());
            v.setKapasitas(Integer.parseInt(txtKapasitas.getText()));
            v.setHarga(Double.parseDouble(txtHarga.getText()));
            v.setStatus(cmbStatus.getSelectedItem().toString());
            v.setDeskripsi(txtDeskripsi.getText().trim());
            v.setGambar(txtGambar.getText().trim());

            if (villaDAO.insertVilla(v)) {
                JOptionPane.showMessageDialog(this, "Data villa berhasil ditambahkan!");
                loadDataToTable();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int viewRow = tblVilla.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih villa yang ingin diubah terlebih dahulu!");
            return;
        }
        if (!validasiFormVilla()) return;
        int modelRow = tblVilla.convertRowIndexToModel(viewRow);

        try {
            int id = (int) tableModel.getValueAt(modelRow, 0);
            Villa v = new Villa();
            v.setId(id);
            v.setNama(txtNama.getText());
            v.setKapasitas(Integer.parseInt(txtKapasitas.getText()));
            v.setHarga(Double.parseDouble(txtHarga.getText()));
            v.setStatus(cmbStatus.getSelectedItem().toString());
            v.setDeskripsi(txtDeskripsi.getText().trim());
            v.setGambar(txtGambar.getText().trim());

            if (villaDAO.updateVilla(v)) {
                JOptionPane.showMessageDialog(this, "Data villa berhasil diperbarui!");
                loadDataToTable();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int viewRow = tblVilla.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih villa yang ingin dihapus terlebih dahulu!");
            return;
        }
        int modelRow = tblVilla.convertRowIndexToModel(viewRow);

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus villa ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(modelRow, 0);
            if (villaDAO.deleteVilla(id)) {
                JOptionPane.showMessageDialog(this, "Data villa berhasil dihapus!");
                loadDataToTable();
                clearForm();
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new DashboardAdminFrame(currentUser).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new KelolaVillaFrameAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnDataPenyewa;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKelolaTransaksi;
    private javax.swing.JButton btnKelolaVilla;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelDeskripsi;
    private javax.swing.JLabel jLabelGambar;
    private javax.swing.JLabel jLabelHarga;
    private javax.swing.JLabel jLabelKapasitas;
    private javax.swing.JLabel jLabelNama;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane scrollDeskripsi;
    private javax.swing.JTable tblVilla;
    private javax.swing.JTextArea txtDeskripsi;
    private javax.swing.JTextField txtGambar;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKapasitas;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}