package client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.function.Consumer;

// Main application frame with CardLayout for modular pages
public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cards = new JPanel(cardLayout);

    private ChatPanel chatPanel;
    private WhiteboardPanel whiteboardPanel;
    private TicTacToePanel gamePanel;
    private QuizPanel quizPanel;

    private WSClient wsClient;
    private String username;
    private String role;

    public MainFrame(String username, String role) {
        super("CollabChat - " + username + " (" + role + ")");
        this.username = username; this.role = role;

        chatPanel = new ChatPanel(this::sendMessage);
        whiteboardPanel = new WhiteboardPanel(this::sendMessage);
        gamePanel = new TicTacToePanel(this::sendMessage);
        quizPanel = new QuizPanel(this::sendMessage, role);

        cards.add(chatPanel, "chat");
        cards.add(whiteboardPanel, "whiteboard");
        cards.add(gamePanel, "game");
        cards.add(quizPanel, "quiz");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createToolbar(), BorderLayout.NORTH);
        getContentPane().add(cards, BorderLayout.CENTER);
    }

    private JPanel createToolbar() {
        JPanel p = new JPanel();
        JButton bChat = new JButton("Chat"); bChat.addActionListener(e -> cardLayout.show(cards, "chat"));
        JButton bBoard = new JButton("Whiteboard"); bBoard.addActionListener(e -> cardLayout.show(cards, "whiteboard"));
        JButton bGame = new JButton("Games"); bGame.addActionListener(e -> cardLayout.show(cards, "game"));
        JButton bQuiz = new JButton("Quiz"); bQuiz.addActionListener(e -> cardLayout.show(cards, "quiz"));
        p.add(bChat); p.add(bBoard); p.add(bGame); p.add(bQuiz);
        return p;
    }

    // Create WS connection and wire incoming messages to panels
    public void connectToServer(String uri) {
        try {
            wsClient = new WSClient(new URI(uri), msg -> handleServerMessage(msg));
            wsClient.connect();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Send message via WS
    public void sendMessage(String msg) {
        if (wsClient != null && wsClient.isOpen()) {
            wsClient.send(msg);
        } else {
            System.out.println("WS not connected: " + msg);
        }
    }

    // Dispatch incoming JSON messages to panels (simple type dispatch)
    private void handleServerMessage(String msg) {
        // In a real app, use a JSON library. For brevity, do simple contains checks.
        SwingUtilities.invokeLater(() -> {
            if (msg.contains("\"type\":\"chat\"")) {
                chatPanel.onMessage(msg);
            } else if (msg.contains("\"type\":\"draw\"")) {
                whiteboardPanel.onDrawMessage(msg);
            } else if (msg.contains("\"type\":\"quiz\"")) {
                quizPanel.onQuizMessage(msg);
            } else if (msg.contains("\"type\":\"game\"")) {
                gamePanel.onGameMessage(msg);
            }
        });
    }

    // Small WebSocket client wrapper
    private static class WSClient extends WebSocketClient {
        private Consumer<String> onMessage;
        public WSClient(URI serverUri, Consumer<String> onMessage) { super(serverUri); this.onMessage = onMessage; }
        @Override public void onOpen(ServerHandshake handshakedata) { System.out.println("WS connected"); }
        @Override public void onMessage(String message) { onMessage.accept(message); }
        @Override public void onClose(int code, String reason, boolean remote) { System.out.println("WS closed: "+reason); }
        @Override public void onError(Exception ex) { ex.printStackTrace(); }
    }
}
