package GUI.andi;

import DAO.UserDAO;
import Model.User;
import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {

    private UserDAO userDAO = new UserDAO();
    private JTextField txtUsername, txtNama;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JLabel lblError;
    private JButton btnRegister, btnBatal;

    public RegisterDialog(Window owner) {
        super(owner, "Register Akun", ModalityType.APPLICATION_MODAL);
        setSize(380, 450);
        setLocationRelativeTo(owner);
        setResizable(false);
        initComponents();
        getRootPane().setDefaultButton(btnRegister);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Register Akun Baru");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(26, 50, 82));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Buat akun admin atau petugas baru");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(100, 100, 100));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblError.setForeground(new Color(180, 0, 0));
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtNama = new JTextField();
        txtUsername = new JTextField();
        txtPassword = new JPasswordField();
        cmbRole = new JComboBox<>(new String[]{"admin", "petugas"});

        btnRegister = new JButton("Daftar");
        btnRegister.setBackground(new Color(26, 50, 82));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegister.setFocusPainted(false);
        btnRegister.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRegister.addActionListener(e -> daftar());

        btnBatal = new JButton("Batal");
        btnBatal.setBackground(new Color(200, 200, 200));
        btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBatal.setFocusPainted(false);
        btnBatal.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBatal.addActionListener(e -> dispose());

        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblError);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Nama Lengkap"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtNama);
        panel.add(Box.createVerticalStrut(12));
        panel.add(new JLabel("Username"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtUsername);
        panel.add(Box.createVerticalStrut(12));
        panel.add(new JLabel("Password"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtPassword);
        panel.add(Box.createVerticalStrut(12));
        panel.add(new JLabel("Role"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(cmbRole);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnRegister);
        panel.add(Box.createVerticalStrut(8));
        panel.add(btnBatal);

        for (Component c : panel.getComponents()) {
            if (c instanceof JTextField || c instanceof JPasswordField || c instanceof JComboBox) {
                ((JComponent) c).setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                ((JComponent) c).setPreferredSize(new Dimension(300, 35));
            }
            if (c instanceof JButton) {
                ((JButton) c).setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                ((JButton) c).setPreferredSize(new Dimension(300, 40));
            }
        }

        getContentPane().add(panel);
    }

    private void daftar() {
        String nama = txtNama.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String role = cmbRole.getSelectedItem().toString();

        if (nama.isEmpty() || username.isEmpty() || password.isEmpty()) {
            lblError.setText("Semua field harus diisi!");
            return;
        }
        if (password.length() < 6) {
            lblError.setText("Password minimal 6 karakter!");
            return;
        }

        User u = new User();
        u.setNama(nama);
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);

        if (userDAO.addUser(u)) {
            JOptionPane.showMessageDialog(this, "Akun berhasil dibuat!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            lblError.setText("Gagal! Username mungkin sudah dipakai.");
        }
    }
}
