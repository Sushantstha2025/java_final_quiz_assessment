package finalAssesment3;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;  // Changed to JPasswordField - better!
    private DBHandler dbHandler = new DBHandler();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginGUI frame = new LoginGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoginGUI() {
        setTitle("Login");
        setBackground(new Color(192, 192, 192));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 756, 531);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("LOGIN PAGE");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblTitle.setBounds(283, 23, 142, 27);
        contentPane.add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblUsername.setBounds(247, 94, 76, 17);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblPassword.setBounds(247, 140, 76, 17);
        contentPane.add(lblPassword);

        txtUsername = new JTextField();
        txtUsername.setBounds(339, 94, 180, 25);
        contentPane.add(txtUsername);
        txtUsername.setColumns(10);

        txtPassword = new JPasswordField(); 
        txtPassword.setBounds(339, 140, 180, 25);
        contentPane.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginGUI.this,
                            "Please enter both username and password.",
                            "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String role = dbHandler.validateLogin(username, password);

                if (role != null) {
                    if ("ADMIN".equalsIgnoreCase(role)) {
                        new AdminGUI().setVisible(true);
                    } else {
                        String firstName = getFirstNameFromDB(username);
                        new UserGUI(firstName, username).setVisible(true);
                        dispose();
                    }
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(LoginGUI.this,
                            "Invalid username or password.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLogin.setForeground(new Color(255, 255, 255));
        btnLogin.setBackground(new Color(0, 128, 0));
        btnLogin.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        btnLogin.setBounds(308, 194, 84, 30);
        contentPane.add(btnLogin);

        JLabel lblNoAccount = new JLabel("No Account?");
        lblNoAccount.setForeground(new Color(255, 0, 0));
        lblNoAccount.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblNoAccount.setBounds(226, 247, 84, 17);
        contentPane.add(lblNoAccount);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(e -> {
            new RegisterGUI().setVisible(true);
        });
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        btnRegister.setBackground(new Color(0, 128, 128));
        btnRegister.setBounds(329, 245, 106, 30);
        contentPane.add(btnRegister);
    }

    /**
     * Helper method to get first name from users table
     * Returns username as fallback if firstName is not found
     */
    private String getFirstNameFromDB(String username) {
        try (Connection conn = dbHandler.getConnection()) {
            String query = "SELECT firstName FROM competitors WHERE username = ?";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, username);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String fn = rs.getString("firstName");
                        return (fn != null && !fn.trim().isEmpty()) ? fn : username;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return username; 
    }
}