package client;

import javax.swing.*;
import java.awt.*;

// Main entry for the Swing client. Creates a Login dialog and then the main frame.
public class CollabClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);
            if (!login.isSucceeded()) System.exit(0);

            // Role and username captured from login dialog
            String username = login.getUsername();
            String role = login.getRole();

            MainFrame main = new MainFrame(username, role);
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setSize(1000, 700);
            main.setLocationRelativeTo(null);
            main.setVisible(true);

            // Connect to server and start receiving messages
            main.connectToServer("ws://localhost:8887");
        });
    }
}
