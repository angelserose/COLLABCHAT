package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// Very small collaborative whiteboard: sends draw messages containing line segments.
// Message example: {"type":"draw","user":"u","x1":10,"y1":10,"x2":20,"y2":20}
public class WhiteboardPanel extends JPanel {
    private List<Line2D> lines = new ArrayList<>();
    private int lastX, lastY;
    private Consumer<String> sender;

    public WhiteboardPanel(Consumer<String> sender) {
        this.sender = sender;
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() { public void mousePressed(MouseEvent e) { lastX=e.getX(); lastY=e.getY(); } });
        addMouseMotionListener(new MouseAdapter() { public void mouseDragged(MouseEvent e) { int x=e.getX(), y=e.getY(); lines.add(new Line2D.Float(lastX,lastY,x,y)); repaint(); String json = String.format("{\"type\":\"draw\",\"user\":\"local\",\"x1\":%d,\"y1\":%d,\"x2\":%d,\"y2\":%d}", lastX,lastY,x,y); sender.accept(json); lastX=x; lastY=y; } });
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g; g2.setStroke(new BasicStroke(2)); g2.setColor(Color.BLACK);
        for (Line2D l: lines) g2.draw(l);
    }

    // When a draw message arrives from the server, update local whiteboard
    public void onDrawMessage(String json) {
        try {
            int x1 = Integer.parseInt(extract(json, "x1"));
            int y1 = Integer.parseInt(extract(json, "y1"));
            int x2 = Integer.parseInt(extract(json, "x2"));
            int y2 = Integer.parseInt(extract(json, "y2"));
            lines.add(new Line2D.Float(x1,y1,x2,y2));
            repaint();
        } catch (Exception e) { /* ignore parse errors in demo */ }
    }

    private String extract(String json, String key) {
        int idx = json.indexOf('"'+key+'"'); if (idx<0) return "0";
        int col = json.indexOf(':', idx); int comma = json.indexOf(',', col+1); if (comma<0) comma = json.indexOf('}', col+1);
        return json.substring(col+1, comma).trim();
    }
}
