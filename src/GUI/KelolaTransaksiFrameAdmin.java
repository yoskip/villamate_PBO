package GUI;

import DAO.PenyewaDAO;
import DAO.TransaksiDAO;
import DAO.VillaDAO;
import Model.Penyewa;
import Model.Transaksi;
import Model.User;
import Model.Villa;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class KelolaTransaksiFrameAdmin extends javax.swing.JFrame {

    private TransaksiDAO transaksiDAO = new TransaksiDAO();
    private VillaDAO villaDAO = new VillaDAO();
    private PenyewaDAO penyewaDAO = new PenyewaDAO();
    private User currentUser;
    private DefaultTableModel tableModel;
    private Map<Integer, String> villaMap = new HashMap<>();
    private Map<Integer, String> penyewaMap = new HashMap<>();
    private static final NumberFormat RUPIAH = NumberFormat.getNumberInstance(Locale.of("id", "ID"));

    public KelolaTransaksiFrameAdmin() {
        this(null);
    }

    public KelolaTransaksiFrameAdmin(User user) {
        this.currentUser = user;
        initComponents();
        checkRole();

        initTableLayout();
        loadLookupData();
        loadDataToTable();
        initMenuBar();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportItem = new JMenuItem("Export CSV");
        exportItem.addActionListener(e -> exportToCSV());
        fileMenu.add(exportItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void exportToCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan Export Transaksi");
        chooser.setFileFilter(new FileNameExtensionFilter("CSV File (*.csv)", "csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) {
            file = new File(file.getAbsolutePath() + ".csv");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("ID,Villa,Penyewa,Check-in,Check-out,Total Hari,Total Biaya,Status\n");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < 8; j++) {
                    Object val = tableModel.getValueAt(i, j);
                    writer.write((val != null ? val.toString() : "") + (j < 7 ? "," : ""));
                }
                writer.write("\n");
            }
            JOptionPane.showMessageDialog(this, "Data berhasil diexport ke:\n" + file.getAbsolutePath(), "Export Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal export: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkRole() {
        if (currentUser != null && "petugas".equals(currentUser.getRole())) {
            btnDelete.setVisible(false);
            btnDelete.setEnabled(false);
        }
    }

    private void initTableLayout() {
        tableModel = (DefaultTableModel) tblTransaksi.getModel();
        tblTransaksi.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        tblTransaksi.getColumnModel().getColumn(8).setMinWidth(0);
        tblTransaksi.getColumnModel().getColumn(8).setMaxWidth(0);
        tblTransaksi.getColumnModel().getColumn(8).setWidth(0);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        tblTransaksi.setRowSorter(sorter);

        txtSearch.putClientProperty("JTextField.placeholderText", "Cari transaksi...");
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

        cmbFilterStatus = new javax.swing.JComboBox<>(new String[]{"Semua", "Aktif", "Selesai", "Dibatalkan"});
        cmbFilterStatus.addActionListener(e -> {
            String selected = cmbFilterStatus.getSelectedItem().toString();
            if ("Semua".equals(selected)) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(selected, 7));
            }
        });
    }

    private void loadLookupData() {
        for (Villa v : villaDAO.getAllVilla()) {
            villaMap.put(v.getId(), v.getNama());
        }
        for (Penyewa p : penyewaDAO.getAllPenyewa()) {
            penyewaMap.put(p.getId(), p.getNama());
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<Transaksi> daftar = transaksiDAO.getAllTransaksi();

        for (Transaksi t : daftar) {
            String namaVilla = villaMap.getOrDefault(t.getIdVilla(), "Villa #" + t.getIdVilla());
            String namaPenyewa = penyewaMap.getOrDefault(t.getIdPenyewa(), "Penyewa #" + t.getIdPenyewa());
            String biaya = "Rp " + RUPIAH.format((long) t.getTotalBiaya());
            Object[] row = {
                t.getId(),
                namaVilla,
                namaPenyewa,
                t.getTglCheckin(),
                t.getTglCheckout(),
                t.getTotalHari() + " hari",
                biaya,
                t.getStatus(),
                t.getIdVilla()
            };
            tableModel.addRow(row);
        }
    }

    private void clearForm() {
        cmbStatus.setSelectedIndex(0);
        tblTransaksi.clearSelection();
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
        tblTransaksi = new javax.swing.JTable();
        jLabelStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VillaMate - Kelola Transaksi");
        setPreferredSize(new java.awt.Dimension(1030, 665));

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
        btnDataPenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPenyewaActionPerformed(evt);
            }
        });

        btnKelolaTransaksi.setText("Kelola Transaksi");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jLabelTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabelTitle.setForeground(new java.awt.Color(30, 58, 95));
        jLabelTitle.setText("Kelola Transaksi");

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Villa", "Penyewa", "Check-in", "Check-out", "Total Hari", "Total Biaya", "Status", "id_villa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTransaksi);

        jLabelStatus.setText("Status");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aktif", "Selesai", "Dibatalkan" }));

        btnUpdate.setBackground(new java.awt.Color(30, 107, 165));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Ubah Status");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitle)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelStatus)
                            .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbFilterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabelTitle)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbFilterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTransaksiMouseClicked
        int viewRow = tblTransaksi.getSelectedRow();
        if (viewRow != -1) {
            int modelRow = tblTransaksi.convertRowIndexToModel(viewRow);
            cmbStatus.setSelectedItem(tableModel.getValueAt(modelRow, 7).toString());
        }
    }//GEN-LAST:event_tblTransaksiMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int viewRow = tblTransaksi.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin diubah statusnya!");
            return;
        }
        int modelRow = tblTransaksi.convertRowIndexToModel(viewRow);

        try {
            int id = (int) tableModel.getValueAt(modelRow, 0);
            String statusBaru = cmbStatus.getSelectedItem().toString();

            if (!transaksiDAO.updateStatus(id, statusBaru)) {
                return;
            }

            if (statusBaru.equals("Selesai") || statusBaru.equals("Dibatalkan")) {
                int idVilla = (int) tableModel.getValueAt(modelRow, 8);
                villaDAO.updateStatus(idVilla, "Tersedia");
            }

            loadDataToTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Status transaksi berhasil diperbarui!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui status: " + e.getMessage());
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int viewRow = tblTransaksi.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi yang ingin dihapus!");
            return;
        }
        int modelRow = tblTransaksi.convertRowIndexToModel(viewRow);

        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus transaksi ini?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tableModel.getValueAt(modelRow, 0);
                String statusTransaksi = tableModel.getValueAt(modelRow, 7).toString();
                int idVilla = (int) tableModel.getValueAt(modelRow, 8);

                if (transaksiDAO.deleteTransaksi(id)) {
                    if ("Aktif".equals(statusTransaksi)) {
                        villaDAO.updateStatus(idVilla, "Tersedia");
                    }
                    loadDataToTable();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus transaksi: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new DashboardAdminFrame(currentUser).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnKelolaVillaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelolaVillaActionPerformed
        new KelolaVillaFrameAdmin(currentUser).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKelolaVillaActionPerformed

    private void btnDataPenyewaActionPerformed(java.awt.event.ActionEvent evt) {
        new DataPenyewaFrameAdmin(currentUser).setVisible(true);
        this.dispose();
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        new LandingPage().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new KelolaTransaksiFrameAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDataPenyewa;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnKelolaTransaksi;
    private javax.swing.JButton btnKelolaVilla;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbFilterStatus;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
