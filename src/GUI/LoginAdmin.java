package GUI;

import DAO.UserDAO;
import Model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class LoginAdmin extends JDialog {

    private UserDAO userDAO = new UserDAO();

    public LoginAdmin(Window owner) {
        super(owner, "Login Admin", ModalityType.APPLICATION_MODAL);
        initComponents();
        setSize(350, 380);
        setLocationRelativeTo(owner);
        getRootPane().setDefaultButton(btnLogin);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new JPanel();
        lblTitle = new JLabel();
        lblSubtitle = new JLabel();
        lblUsername = new JLabel();
        txtUsername = new JTextField();
        lblPassword = new JLabel();
        txtPassword = new JPasswordField();
        lblError = new JLabel();
        btnLogin = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Admin");
        setResizable(false);

        jPanel1.setBackground(Color.WHITE);

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(26, 50, 82));
        lblTitle.setText("Login Admin");

        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(80, 80, 80));
        lblSubtitle.setText("Masukkan username dan password admin");

        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblUsername.setForeground(new Color(26, 50, 82));
        lblUsername.setText("Username");

        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPassword.setForeground(new Color(26, 50, 82));
        lblPassword.setText("Password");

        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        lblError.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblError.setForeground(new Color(180, 0, 0));
        lblError.setText("");

        btnLogin.setBackground(new Color(26, 50, 82));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setText("Login");
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(evt -> btnLoginActionPerformed(evt));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitle)
                        .addComponent(lblSubtitle)
                        .addComponent(lblUsername)
                        .addComponent(txtUsername, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addComponent(lblPassword)
                        .addComponent(txtPassword, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                        .addComponent(lblError))
                    .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(lblTitle)
                    .addGap(5, 5, 5)
                    .addComponent(lblSubtitle)
                    .addGap(20, 20, 20)
                    .addComponent(lblUsername)
                    .addGap(5, 5, 5)
                    .addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addGap(10, 10, 10)
                    .addComponent(lblPassword)
                    .addGap(5, 5, 5)
                    .addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addGap(5, 5, 5)
                    .addComponent(lblError)
                    .addGap(15, 15, 15)
                    .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30))
        );

        getContentPane().add(jPanel1, BorderLayout.CENTER);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username dan password harus diisi!");
            return;
        }

        User user = userDAO.login(username, password);
        if (user != null) {
            lblError.setText("");
            dispose();
            DashboardAdminFrame dashboard = new DashboardAdminFrame(user);
            dashboard.setVisible(true);
        } else {
            lblError.setText("Username atau password salah!");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel jPanel1;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblPassword;
    private JPasswordField txtPassword;
    private JLabel lblError;
    private JButton btnLogin;
    // End of variables declaration//GEN-END:variables
}
