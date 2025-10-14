package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Simple login dialog that collects username and role (teacher/student). No server auth in this demo.
public class LoginDialog extends JDialog {
    private JTextField tfUsername;
    private JComboBox<String> cbRole;
    private boolean succeeded = false;

    public LoginDialog(Frame parent) {
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridLayout(0,2));
        panel.add(new JLabel("Username:"));
        tfUsername = new JTextField(20);
        panel.add(tfUsername);
        panel.add(new JLabel("Role:"));
        cbRole = new JComboBox<>(new String[]{"student","teacher"});
        panel.add(cbRole);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getUsername().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Enter username");
                    return;
                }
                succeeded = true;
                setVisible(false);
            }
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> System.exit(0));

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
    }

    public String getUsername() { return tfUsername.getText().trim(); }
    public String getRole() { return (String) cbRole.getSelectedItem(); }
    public boolean isSucceeded() { return succeeded; }
}
