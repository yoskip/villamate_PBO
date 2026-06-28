package GUI;

import DAO.PenyewaDAO;
import Model.Penyewa;
import Model.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class DataPenyewaFrameAdmin extends javax.swing.JFrame {

    private PenyewaDAO penyewaDAO = new PenyewaDAO();
    private User currentUser;
    private DefaultTableModel tableModel;

    public DataPenyewaFrameAdmin() {
        this(null);
    }

    public DataPenyewaFrameAdmin(User user) {
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
        tableModel = (DefaultTableModel) tblPenyewa.getModel();
        tblPenyewa.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblPenyewa.setRowSorter(sorter);

        txtSearch.putClientProperty("JTextField.placeholderText", "Cari penyewa...");
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
        List<Penyewa> daftar = penyewaDAO.getAllPenyewa();

        for (Penyewa p : daftar) {
            Object[] row = {
                p.getId(),
                p.getNama(),
                p.getNoKtp(),
                p.getNoHp(),
                p.getAlamat()
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        txtNama.setText("");
        txtNoKtp.setText("");
        txtNoHp.setText("");
        txtAlamat.setText("");
        tblPenyewa.clearSelection();
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
        tblPenyewa = new javax.swing.JTable();
        jLabelNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabelKtp = new javax.swing.JLabel();
        txtNoKtp = new javax.swing.JTextField();
        jLabelHp = new javax.swing.JLabel();
        txtNoHp = new javax.swing.JTextField();
        jLabelAlamat = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VillaMate - Data Penyewa");
        setPreferredSize(new java.awt.Dimension(1030, 720));

        jPanel6.setBackground(new java.awt.Color(0, 0, 102));

        jButton1.setText("Dashboard");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnKelolaVilla.setText("Kelola Villa");
        btnKelolaVilla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelolaVillaActionPerformed(evt);
            }
        });

        btnDataPenyewa.setText("Data Penyewa");

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
                .addGap(31, 31, 31))
        );

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(30, 58, 95));
        jLabelTitle.setText("Data Penyewa");

        tblPenyewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nama", "No. KTP", "No. HP", "Alamat"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenyewaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPenyewa);

        jLabelNama.setText("Nama");

        jLabelKtp.setText("No. KTP");

        jLabelHp.setText("No. HP");

        jLabelAlamat.setText("Alamat");

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
                            .addComponent(jLabelKtp)
                            .addComponent(txtNoKtp)
                            .addComponent(jLabelHp)
                            .addComponent(txtNoHp)
                            .addComponent(jLabelAlamat)
                            .addComponent(txtAlamat)
                            .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jLabelKtp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNoKtp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelHp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNoHp, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabelAlamat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblPenyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenyewaMouseClicked
        int viewRow = tblPenyewa.getSelectedRow();
        if (viewRow != -1) {
            int modelRow = tblPenyewa.convertRowIndexToModel(viewRow);
            txtNama.setText(tableModel.getValueAt(modelRow, 1).toString());
            txtNoKtp.setText(tableModel.getValueAt(modelRow, 2).toString());
            txtNoHp.setText(tableModel.getValueAt(modelRow, 3).toString());
            txtAlamat.setText(tableModel.getValueAt(modelRow, 4).toString());
        }
    }//GEN-LAST:event_tblPenyewaMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int viewRow = tblPenyewa.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih penyewa yang ingin diubah terlebih dahulu!");
            return;
        }
        int modelRow = tblPenyewa.convertRowIndexToModel(viewRow);

        try {
            int id = (int) tableModel.getValueAt(modelRow, 0);
            Penyewa p = new Penyewa();
            p.setId(id);
            p.setNama(txtNama.getText());
            p.setNoKtp(txtNoKtp.getText());
            p.setNoHp(txtNoHp.getText());
            p.setAlamat(txtAlamat.getText());

            if (penyewaDAO.updatePenyewa(p)) {
                JOptionPane.showMessageDialog(this, "Data penyewa berhasil diperbarui!");
                loadDataToTable();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int viewRow = tblPenyewa.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih penyewa yang ingin dihapus terlebih dahulu!");
            return;
        }
        int modelRow = tblPenyewa.convertRowIndexToModel(viewRow);

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data penyewa ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tableModel.getValueAt(modelRow, 0);
                if (penyewaDAO.deletePenyewa(id)) {
                    JOptionPane.showMessageDialog(this, "Data penyewa berhasil dihapus!");
                    loadDataToTable();
                    clearForm();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
        String nama = txtNama.getText().trim();
        String noKtp = txtNoKtp.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String alamat = txtAlamat.getText().trim();

        if (nama.isEmpty() || noKtp.isEmpty() || noHp.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (penyewaDAO.getPenyewaByNoKtp(noKtp) != null) {
            JOptionPane.showMessageDialog(this, "No. KTP sudah terdaftar!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Penyewa p = new Penyewa(0, nama, noKtp, noHp, alamat);
        int id = penyewaDAO.addPenyewa(p);
        if (id != -1) {
            JOptionPane.showMessageDialog(this, "Data penyewa berhasil ditambahkan!");
            loadDataToTable();
            clearForm();
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new DashboardAdminFrame(currentUser).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnKelolaVillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaVillaActionPerformed
        new KelolaVillaFrameAdmin(currentUser).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKelolaVillaActionPerformed

    private void btnKelolaTransaksiActionPerformed(java.awt.event.ActionEvent evt) {
        new KelolaTransaksiFrameAdmin(currentUser).setVisible(true);
        this.dispose();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LandingPage().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DataPenyewaFrameAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDataPenyewa;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKelolaTransaksi;
    private javax.swing.JButton btnKelolaVilla;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelAlamat;
    private javax.swing.JLabel jLabelHp;
    private javax.swing.JLabel jLabelKtp;
    private javax.swing.JLabel jLabelNama;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPenyewa;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoHp;
    private javax.swing.JTextField txtNoKtp;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
